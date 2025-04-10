"use client"

import { useState, FormEvent, ChangeEvent } from 'react'
import { useRouter } from 'next/navigation'
import '../styles/login.css'

interface FormData {
  role: string;
  username: string;
  password: string;
}

export default function LoginForm() {
  const router = useRouter()
  const [formData, setFormData] = useState<FormData>({
    role: "",
    username: "",
    password: "",
  })
  const [isLoading, setIsLoading] = useState<boolean>(false)
  const [formError, setFormError] = useState<string>("")

  const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }))
    
    // Limpiar errores cuando el usuario comienza a escribir
    if (formError) setFormError("")
  }

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault()
    setFormError("")

    // Validar que todos los campos estén completos
    if (!formData.role || !formData.username || !formData.password) {
      setFormError("Por favor, complete todos los campos.")
      return
    }

    // Simular carga
    setIsLoading(true)
    
    // Simulamos un tiempo de procesamiento para mostrar el estado de carga
    setTimeout(() => {
      setIsLoading(false)
      
      // Imprimir en consola según el rol
      switch (formData.role) {
        case "admin":
          console.log("Rol de administrador")
          break
        case "cajero":
          console.log("Rol de cajero")
          break
        case "cocina":
          console.log("Rol de cocina")
          break
        default:
          console.log("Rol no reconocido")
      }
    }, 800)
  }

  return (
    <div className="login-container">
      <div className="background-pattern"></div>
      <div className="login-form-container">
        <div className="login-card">
          <div className="login-header">
            <h1>FOODLY</h1>
          </div>
          <div className="login-body">
            
            {formError && (
              <div style={{
                padding: '10px',
                marginBottom: '20px',
                backgroundColor: '#FFF0F0',
                color: '#E53935',
                borderRadius: '6px',
                fontSize: '14px',
                textAlign: 'center'
              }}>
                {formError}
              </div>
            )}
            
            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <label htmlFor="role" className="form-label">
                  Seleccionar Rol
                </label>
                <div className="form-select-container">
                  <select
                    className="form-select"
                    id="role"
                    name="role"
                    value={formData.role}
                    onChange={handleChange}
                    required
                    disabled={isLoading}
                  >
                    <option value="" disabled>
                      Seleccione su rol
                    </option>
                    <option value="admin">Administrador</option>
                    <option value="cajero">Cajero</option>
                    <option value="cocina">Cocina</option>
                  </select>
                  <span className="select-icon"></span>
                </div>
              </div>

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
                  <span className="icon user-icon"></span>
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
                  <span className="icon lock-icon"></span>
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
  )
}