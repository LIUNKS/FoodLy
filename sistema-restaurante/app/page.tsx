import { Metadata } from "next"
import { Suspense } from "react"
import LoginForm from "@/components/login-form"

// Definir la metadata para la página login (ruta raíz)
export const metadata: Metadata = {
  title: "FoodLy - Login",
}

// Componente servidor que envuelve el componente cliente
export default function LoginPage() {
  return (
    <Suspense fallback={<div>Cargando...</div>}>
      <LoginForm />
    </Suspense>
  )
}
