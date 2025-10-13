// script.js
const API_BASE_URL = 'http://localhost:8080';

// Mostrar/ocultar contraseña
document.getElementById('toggleLoginPassword').addEventListener('click', function() {
    togglePasswordVisibility('loginPassword', this);
});

document.getElementById('toggleRegisterPassword').addEventListener('click', function() {
    togglePasswordVisibility('registerPassword', this);
});

function togglePasswordVisibility(passwordFieldId, button) {
    const passwordField = document.getElementById(passwordFieldId);
    const icon = button.querySelector('i');
    
    if (passwordField.type === 'password') {
        passwordField.type = 'text';
        icon.classList.remove('fa-eye');
        icon.classList.add('fa-eye-slash');
    } else {
        passwordField.type = 'password';
        icon.classList.remove('fa-eye-slash');
        icon.classList.add('fa-eye');
    }
}

// Cambiar entre formularios
function showForm(formId) {
    document.querySelectorAll('.form-container').forEach(form => {
        form.style.display = 'none';
    });
    document.getElementById(formId).style.display = 'block';
}

// Manejar registro - FUNCIONANDO (NO CAMBIAR)
document.getElementById('registerFormElement').addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const registerData = {
        nombre: document.getElementById('registerName').value,
        email: document.getElementById('registerEmail').value,
        contraseña: document.getElementById('registerPassword').value,
        rol: document.getElementById('registerRol').value
    };

    const button = this.querySelector('button[type="submit"]');
    const originalText = button.innerHTML;
    
    try {
        // Mostrar loading
        button.innerHTML = '<i class="fas fa-spinner fa-spin me-2"></i>Creando cuenta...';
        button.disabled = true;

        console.log('📤 Enviando datos:', registerData);

        const response = await fetch(`${API_BASE_URL}/api/usuarios/registro`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(registerData)
        });

        console.log('📥 Respuesta recibida, status:', response.status);

        if (response.ok) {
            const usuario = await response.json();
            showAlert('¡Cuenta creada exitosamente! Redirigiendo al login...', 'success');
            
            // Limpiar formulario
            this.reset();
            
            // Mostrar login después de 2 segundos
            setTimeout(() => {
                showForm('loginForm');
            }, 2000);
        } else {
            let errorMessage = 'Error al crear la cuenta';

try {
    const contentType = response.headers.get('content-type');

    if (contentType && contentType.includes('application/json')) {
        const errorData = await response.json();
        const msg = errorData.message || '';

        if (msg.includes('Duplicate entry')) {
            errorMessage = 'Correo ya inscrito';
        } else {
            errorMessage = msg || errorMessage;
        }
    } else {
        const text = await response.text();

        if (text.includes('Duplicate entry')) {
            errorMessage = 'Correo ya inscrito';
        } else if (text) {
            errorMessage = text;
        }
    }
} catch (readError) {
    console.warn('No se pudo leer el cuerpo del error:', readError);
}

showAlert(errorMessage, 'danger');

            showAlert(errorMessage, 'danger');
        }
    } catch (error) {
        console.error('❌ Error de conexión:', error);
        showAlert('Error de conexión: ' + error.message, 'danger');
    } finally {
        button.innerHTML = originalText;
        button.disabled = false;
    }
});

// Manejar login - ACTUALIZADO siguiendo el mismo patrón que registro
document.getElementById('loginFormElement').addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const loginData = {
        email: document.getElementById('loginEmail').value,
        contraseña: document.getElementById('loginPassword').value
    };

    const button = this.querySelector('button[type="submit"]');
    const originalText = button.innerHTML;
    
    try {
        button.innerHTML = '<i class="fas fa-spinner fa-spin me-2"></i>Iniciando sesión...';
        button.disabled = true;

        const response = await fetch(`${API_BASE_URL}/api/usuarios/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(loginData)
        });

        if (response.ok) {
            const usuario = await response.json();

            if (usuario.rol === 'DUEÑO') {
                showAlert('¡Iniciaste sesión correctamente!', 'success');
                localStorage.setItem('usuario', JSON.stringify(usuario));
                setTimeout(() => {
                    window.location.href = '/inicio';
                }, 2000);
            } else {
                showAlert('Acceso denegado: no tienes permisos para esta sección.', 'danger');
                // Opcional: limpiar inputs o algo más
            }
            
        } else {
            let errorMessage = 'Correo o contraseña incorrectos';
            try {
                const contentType = response.headers.get('content-type');
                if (contentType && contentType.includes('application/json')) {
                    const errorData = await response.json();
                    errorMessage = errorData.message || errorMessage;
                } else {
                    const text = await response.text();
                    if (text) errorMessage = text;
                }
            } catch {
                // error ignorado
            }
            showAlert(errorMessage, 'danger');
        }
    } catch (error) {
        showAlert('Error de conexión: ' + error.message, 'danger');
    } finally {
        button.innerHTML = originalText;
        button.disabled = false;
    }
});

    
// Manejar recuperación de contraseña
document.getElementById('forgotFormElement').addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const email = document.getElementById('forgotEmail').value;
    
    showAlert('Función de recuperación en desarrollo. Contacta al administrador.', 'info');
    
    this.reset();
});

// Mostrar alertas
function showAlert(message, type) {
    document.querySelectorAll('.alert').forEach(alert => alert.remove());
    
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type} alert-dismissible fade show`;
    alertDiv.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
    
    const activeForm = document.querySelector('.form-container[style="display: block;"]');
    if (activeForm) {
        activeForm.insertBefore(alertDiv, activeForm.firstChild);
    }
    
    setTimeout(() => {
        if (alertDiv.parentElement) {
            alertDiv.remove();
        }
    }, 5000);
}
// Verificar si ya está autenticado - CORREGIDO
function checkAuthentication() {
    const usuario = localStorage.getItem('usuario');
    
    // Obtener la página actual
    const paginaActual = window.location.pathname;

    // Solo redirigir si estamos en el login Y ya hay un usuario logueado
    if (usuario && paginaActual.includes('login.html')) {
        console.log("✅ Usuario autenticado, redirigiendo a inicio.html");
        window.location.href = '/inicio'; // Usa tu endpoint del controlador
    }
    // Si no hay usuario y estamos en el login, no hacer nada (permanecer en login)
}

// Inicializar
document.addEventListener('DOMContentLoaded', function() {
    checkAuthentication();
    showForm('loginForm');
});
function showAlert(message, type) {
    document.querySelectorAll('.alert').forEach(alert => alert.remove());
    
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type} alert-dismissible fade show`;
    alertDiv.style.position = 'absolute';         // Posición absoluta
    alertDiv.style.top = '10px';                  // Ajusta según donde quieres que aparezca
    alertDiv.style.left = '50%';                   // Centrar horizontalmente
    alertDiv.style.transform = 'translateX(-50%)'; // Ajuste para centrar perfecto
    alertDiv.style.zIndex = '1050';                // Para que esté encima de otros elementos
    alertDiv.style.width = 'auto';                  // Que no ocupe todo el ancho (puedes ajustar)
    alertDiv.style.minWidth = '300px';              // Opcional, para que tenga buen tamaño
    alertDiv.style.opacity = '0.9';                  // Un poco transparente, si quieres

    alertDiv.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
    
    const activeForm = document.querySelector('.form-container[style="display: block;"]');
    if (activeForm) {
        // En vez de insertarlo dentro del form (que cambia su tamaño), lo pones en body
        document.body.appendChild(alertDiv);
    }
    
    setTimeout(() => {
        if (alertDiv.parentElement) {
            alertDiv.remove();
        }
    }, 5000);
}
