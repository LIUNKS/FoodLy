import { Metadata } from "next"
import "@/styles/common.css" // Importar estilos comunes para asegurar que se usen correctamente

export const metadata: Metadata = {
  title: "Arqueo",
}

export default function ArqueoLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return <>{children}</>
}