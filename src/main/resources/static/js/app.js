// Basic progressive enhancement for subtle interactions
document.addEventListener('DOMContentLoaded', () => {
  // Ripple-like focus ring for buttons (CSS-only fallback exists)
  const buttons = document.querySelectorAll('.btn');
  buttons.forEach((button) => {
    button.addEventListener('click', () => {
      button.classList.add('is-pressed');
      window.setTimeout(() => button.classList.remove('is-pressed'), 180);
    });
  });

  // Animate table on initial load
  const table = document.querySelector('.table');
  if (table) {
    table.style.opacity = '0';
    table.style.transition = 'opacity 240ms ease';
    requestAnimationFrame(() => {
      table.style.opacity = '1';
    });
  }

  // Auto-dismiss notifications after 5 seconds
  const alerts = document.querySelectorAll('.alert');
  alerts.forEach((alert) => {
    setTimeout(() => {
      if (alert && alert.parentNode) {
        const bsAlert = new bootstrap.Alert(alert);
        bsAlert.close();
      }
    }, 5000);
  });

  // Date validation for maintenance form
  const serviceDateInput = document.querySelector('input[name="serviceDate"]');
  const estimatedReadinessInput = document.querySelector('input[name="estimatedReadiness"]');
  
  if (serviceDateInput && estimatedReadinessInput) {
    function validateDates() {
      const serviceDate = new Date(serviceDateInput.value);
      const estimatedReadiness = new Date(estimatedReadinessInput.value);
      
      // Clear previous validation messages
      const existingError = estimatedReadinessInput.parentNode.querySelector('.date-validation-error');
      if (existingError) {
        existingError.remove();
      }
      
      // Only validate if both dates are provided
      if (serviceDateInput.value && estimatedReadinessInput.value) {
        if (estimatedReadiness <= serviceDate) {
          const errorDiv = document.createElement('div');
          errorDiv.className = 'text-danger date-validation-error mt-1';
          errorDiv.textContent = 'Estimated readiness date must be after service date';
          estimatedReadinessInput.parentNode.appendChild(errorDiv);
          estimatedReadinessInput.setCustomValidity('Estimated readiness date must be after service date');
        } else {
          estimatedReadinessInput.setCustomValidity('');
        }
      }
    }
    
    // Add event listeners for real-time validation
    serviceDateInput.addEventListener('change', validateDates);
    estimatedReadinessInput.addEventListener('change', validateDates);
    estimatedReadinessInput.addEventListener('input', validateDates);
  }
});






