import os
import re

# Base directory for admin templates
target_dir = r"c:\Users\abhij\Downloads\online_exam_system\online_exam_system\src\main\resources\templates\admin"

# Link tag to inject
link_tag = '<link rel="stylesheet" th:href="@{/css/admin-theme.css}">'

# Style block to remove
style_block_pattern = re.compile(r'<style>\s*body\s*\{\s*background-color:\s*#f8f9fa;\s*\}\s*</style>', re.IGNORECASE)

for root, _, files in os.walk(target_dir):
    for filename in files:
        if filename.endswith(".html") and filename != "dashboard.html": # Skip dashboard as requested
            filepath = os.path.join(root, filename)
            
            with open(filepath, 'r', encoding='utf-8') as f:
                content = f.read()
            
            # Remove inline style if exists
            content = style_block_pattern.sub('', content)
            
            # Add <link> inside <head> if not already there
            if "admin-theme.css" not in content and "<head>" in content:
                content = content.replace("</head>", f"  {link_tag}\n</head>")
            
            # Add entry animation to main container
            # Only replace first occurrence if it doesn't already have animate classes
            if 'class="container mt-5"' in content:
                content = content.replace('class="container mt-5"', 'class="container mt-5 animate-fade-in"')
                
            # Add slide-up to card
            if 'class="card shadow-sm border-0"' in content:
                content = content.replace('class="card shadow-sm border-0"', 'class="card shadow-sm border-0 animate-slide-up delay-1"')
            elif 'class="card border-0 shadow-sm"' in content:
                content = content.replace('class="card border-0 shadow-sm"', 'class="card border-0 shadow-sm animate-slide-up delay-1"')
            elif 'class="card border-0 shadow-sm mx-auto"' in content:
                content = content.replace('class="card border-0 shadow-sm mx-auto"', 'class="card border-0 shadow-sm mx-auto animate-slide-up delay-1"')

            with open(filepath, 'w', encoding='utf-8') as f:
                f.write(content)
            
            print(f"Processed: {filename}")
