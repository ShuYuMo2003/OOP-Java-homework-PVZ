import os
from PIL import Image
from tqdm import tqdm

def get_all_png_files(directory):
    png_files = []
    for root, dirs, files in os.walk(directory):
        for file in files:
            if file.lower().endswith('.png'):
                png_files.append(os.path.join(root, file))
    return png_files


directory_path = '.'
png_files = get_all_png_files(directory_path)
for png_file in tqdm(png_files):
    img = Image.open(png_file).convert("RGBA")
    width, height = img.size
    crop_area = (0, height * 0.62, width, height)  # 这里的数值根据实际情况调整

    cropped_image = img.crop(crop_area)

    # cropped_image.show()
    img.save(png_file)