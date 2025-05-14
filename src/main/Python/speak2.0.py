import sys
import subprocess
import os
try:
    import torch
except ImportError:
    print("PyTorch is not installed. Please install it with pip.")
    sys.exit(1)
try:
    import intel_extension_for_pytorch as ipex
    ipex_available = True
except ImportError:
    ipex_available = False




def convert_to_wav_if_needed(input_path):
    if input_path.lower().endswith(".wav"):
        return input_path

    output_path = os.path.splitext(input_path)[0] + "_converted.wav"

    command = [
        "ffmpeg", "-y",  # -y = overwrite if exists
        "-i", input_path,
        "-ac", "1",             # Mono
        "-ar", "24000",         # 24kHz sampling rate
        output_path
    ]

    result = subprocess.run(command, capture_output=True, text=True)

    if result.returncode != 0:
        print("FFmpeg conversion failed:")
        print(result.stderr)
        raise Exception("Failed to convert audio")

    return output_path

# Get command-line arguments: text to synthesize, path to reference audio, and output path
if torch.cuda.is_available():
    device = "cuda"
elif hasattr(torch, "xpu") and torch.xpu.is_available():
    device = "xpu"
elif ipex_available:
    device = "xpu"  # Let IPEX handle Intel GPU
else:
    device = "cpu"


print(f"Using device: {device}")
text = sys.argv[1]
reference_audio = convert_to_wav_if_needed(sys.argv[2])
output_path = sys.argv[3]

# Ensure the output directory exists
os.makedirs(os.path.dirname(output_path), exist_ok=True)
model = "F5TTS_v1_Base"

# Command to run F5-TTS
command = [
    "src/main/Python/f5-tts_infer-cli.exe",      # The CLI tool from F5-TTS
    "--model", model,
    "--ref_audio", reference_audio,    # Reference audio path
    "--gen_text", text,              # Text to synthesize
    "--output_file", output_path,      # Where to save the generated audio
    "--speed", "0.9",   # <--- slow down
    "--device", "xpu"
]

# Run the command and wait for it to complete
result = subprocess.run(command, capture_output=True, text=True)

# Print stdout and stderr to help with debugging
print(result.stdout)
print(result.stderr, file=sys.stderr)

# Optional: return the result code (0 for success, nonzero for errors)
sys.exit(result.returncode)
