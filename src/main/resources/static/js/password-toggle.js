document.addEventListener('DOMContentLoaded', function() {
    // Inject CSS for the toggle icon
    const style = document.createElement('style');
    style.textContent = `
        .password-toggle-icon {
            position: absolute;
            right: 15px;
            top: 50%;
            transform: translateY(-50%);
            cursor: pointer;
            color: #64748b;
            z-index: 10;
            transition: color 0.2s ease;
        }
        .password-toggle-icon:hover {
            color: #4f46e5;
        }
    `;
    document.head.appendChild(style);

    const passwordInputs = document.querySelectorAll('input[type="password"]');
    
    passwordInputs.forEach(function(input) {
        const parent = input.parentElement;
        
        // Ensure parent has position relative
        if (window.getComputedStyle(parent).position === 'static') {
            parent.style.position = 'relative';
        }
        
        // Make sure input has enough right padding so text doesn't go under icon
        input.style.paddingRight = '45px';

        // Create eye icon
        const icon = document.createElement('i');
        icon.className = 'bi bi-eye-slash-fill password-toggle-icon';
        
        // Insert icon after input
        input.parentNode.insertBefore(icon, input.nextSibling);
        
        // Add click event to toggle type and icon
        icon.addEventListener('click', function() {
            if (input.type === 'password') {
                input.type = 'text';
                icon.className = 'bi bi-eye-fill password-toggle-icon';
            } else {
                input.type = 'password';
                icon.className = 'bi bi-eye-slash-fill password-toggle-icon';
            }
        });
    });
});
