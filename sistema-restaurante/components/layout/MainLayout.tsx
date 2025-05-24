import type { ReactNode } from "react"
import Sidebar from "./Sidebar"
// app/layout.tsx
//import 'bootstrap/dist/css/bootstrap.min.css'
//import 'bootstrap/dist/js/bootstrap.bundle.min.js'
//import '../styles/globals.css'
interface MainLayoutProps {
  children: ReactNode
}

export default function MainLayout({ children }: MainLayoutProps) {
  return (
    <div className="app-container">
      <Sidebar />
      <div id="main" className="container-fluid">
        {children}
      </div>
    </div>
  )
}
