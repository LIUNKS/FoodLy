import type { ReactNode } from "react"
import Sidebar from "./Sidebar"

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
