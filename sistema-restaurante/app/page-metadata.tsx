import { Metadata } from "next"
import { Suspense } from "react"
import LoginForm from "@/components/login-form"

// Definir la metadata para la página login (ruta raíz)
export const metadata: Metadata = {
  title: "Login",
}

// Componente servidor que envuelve el componente cliente
export default function Page() {
  return (
    <Suspense fallback={<div>Cargando...</div>}>
      <LoginForm />
    </Suspense>
  )
}