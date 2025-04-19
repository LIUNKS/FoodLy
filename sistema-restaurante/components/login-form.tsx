"use client";

import { useState, type FormEvent, type ChangeEvent, useEffect } from "react";
import { useAuth } from "@/hooks/useAuth";
// Asegurarnos de importar los estilos correctos
import "@/styles/login.css";

interface FormData {
  username: string;
  password: string;
}

export default function LoginForm() {
  const { login, isLoading, error } = useAuth();
  const [formData, setFormData] = useState<FormData>({
    username: "",
    password: "",
  });

  useEffect(() => {
    // Log para depuración
    console.log("Estado de isLoading:", isLoading);
  }, [isLoading]);

  const handleChange = (
    e: ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    console.log("Intentando iniciar sesión con:", formData);
    try {
      await login(formData.username, formData.password);
    } catch (err) {
      console.error("Error de inicio de sesión:", err);
    }
  };

  return (
    // Asegurarnos de que el contenedor ocupe toda la pantalla y tenga el centrado correcto
    <div className="login-container" style={{ width: '100vw', height: '100vh', display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
      <div className="background-pattern"></div>
      <div className="login-form-container">
        <div className="login-card">
          <div className="login-header">
            <h1>FOODLY</h1>
          </div>
          <div className="login-body">
            {error && (
              <div
                style={{
                  padding: "10px",
                  marginBottom: "20px",
                  backgroundColor: "#FFF0F0",
                  color: "#E53935",
                  borderRadius: "6px",
                  fontSize: "14px",
                  textAlign: "center",
                }}
              >
                {error}
              </div>
            )}

            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <label htmlFor="username" className="form-label">
                  Usuario
                </label>
                <div className="form-input-container">
                  <input
                    type="text"
                    className="form-input"
                    id="username"
                    name="username"
                    placeholder="Ingrese su usuario"
                    value={formData.username}
                    onChange={handleChange}
                    required
                    disabled={isLoading}
                  />
                  <i className="fas fa-user icon"></i>
                </div>
              </div>

              <div className="form-group">
                <label htmlFor="password" className="form-label">
                  Contraseña
                </label>
                <div className="form-input-container">
                  <input
                    type="password"
                    className="form-input"
                    id="password"
                    name="password"
                    placeholder="Ingrese su contraseña"
                    value={formData.password}
                    onChange={handleChange}
                    required
                    disabled={isLoading}
                  />
                  <i className="fas fa-lock icon"></i>
                </div>
              </div>

              <button
                type="submit"
                className="login-button"
                disabled={isLoading}
              >
                {isLoading ? (
                  <span>Iniciando sesión...</span>
                ) : (
                  <span>Iniciar sesión</span>
                )}
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}
