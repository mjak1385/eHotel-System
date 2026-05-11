document.addEventListener('DOMContentLoaded', () => {
    // Add confirmation dialogs to all Delete forms
    const forms = document.querySelectorAll('form');
    
    forms.forEach(form => {
        const actionInput = form.querySelector('input[name="action"]');
        
        if (actionInput && actionInput.value.includes('delete')) {
            form.addEventListener('submit', function(e) {
                const isConfirmed = confirm("⚠️ Are you sure you want to delete this record? This action is permanent and cannot be undone.");
                
                if (!isConfirmed) {
                    e.preventDefault(); 
                }
            });
        }
    });
});