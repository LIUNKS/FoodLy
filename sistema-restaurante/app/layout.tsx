import type React from "react"
import type { Metadata } from "next"
import { AuthProvider } from "@/context/AuthContext"
import 'bootstrap/dist/css/bootstrap.min.css'
import "./globals.css"
import Providers from "@/components/providers/Providers"

export const metadata: Metadata = {
  title: {
    template: 'FoodLy - %s',
    default: 'FoodLy',
  },
  description: "Sistema de gestión para el restaurante FoodLy",
}

// Exportar viewport como configuración separada según las nuevas recomendaciones de Next.js
export const viewport = {
  width: 'device-width',
  initialScale: 1,
  shrinkToFit: 'no',
}

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode
}>) {  return (
    <html lang="es">
      <head>
        {/* Mantenemos Font Awesome para compatibilidad con el código existente */}
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" />
      </head>
      <body>
        <AuthProvider>
          <Providers>
            {children}
          </Providers>
        </AuthProvider>
      </body>
    </html>
  )
}