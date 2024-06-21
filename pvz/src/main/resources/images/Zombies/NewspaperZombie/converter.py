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
    datas = img.getdata()
    new_data = []
    for item in datas:
        # 更改白色像素为透明
        if item[0] == 255 and item[1] == 255 and item[2] == 255:
            new_data.append((255, 255, 255, 0))  # 将白色像素的alpha值设为0
        else:
            new_data.append(item)
    img.putdata(new_data)
    img.save(png_file)