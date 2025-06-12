"use client";

import { useState, useEffect } from 'react';

interface TokenExpirationAlertProps {
  onSessionExpired?: () => void;
  className?: string;
}

export const TokenExpirationAlert = ({ 
  onSessionExpired,
  className = '' 
}: TokenExpirationAlertProps) => {
  const [showAlert, setShowAlert] = useState(false);

  useEffect(() => {
    // Escuchar eventos de expiración de token
    const handleTokenExpired = () => {
      setShowAlert(true);
      if (onSessionExpired) {
        onSessionExpired();
      }
    };

    // Este componente puede ser activado por el apiClient cuando detecte un 401/403
    window.addEventListener('tokenExpired', handleTokenExpired);

    return () => {
      window.removeEventListener('tokenExpired', handleTokenExpired);
    };
  }, [onSessionExpired]);

  const handleClose = () => {
    setShowAlert(false);
  };

  const handleLogin = () => {
    setShowAlert(false);
    // Redirigir al login
    window.location.href = '/';
  };

  if (!showAlert) {
    return null;
  }

  return (
    <div className={`position-fixed top-0 start-0 w-100 h-100 d-flex align-items-center justify-content-center ${className}`} 
         style={{ backgroundColor: 'rgba(0,0,0,0.5)', zIndex: 9999 }}>
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header bg-warning">
            <h5 className="modal-title">
              <i className="fas fa-exclamation-triangle me-2"></i>
              Sesión Expirada
            </h5>
          </div>
          <div className="modal-body">
            <div className="text-center">
              <i className="fas fa-clock fa-3x text-warning mb-3"></i>
              <p className="mb-3">
                Su sesión ha expirado por motivos de seguridad.
              </p>
              <p className="text-muted">
                Por favor, inicie sesión nuevamente para continuar.
              </p>
            </div>
          </div>
          <div className="modal-footer">
            <button 
              type="button" 
              className="btn btn-secondary" 
              onClick={handleClose}
            >
              Cerrar
            </button>
            <button 
              type="button" 
              className="btn btn-primary" 
              onClick={handleLogin}
            >
              <i className="fas fa-sign-in-alt me-2"></i>
              Iniciar Sesión
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
