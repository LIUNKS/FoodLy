import type { ReactNode } from "react"
import Sidebar from "./Sidebar"

interface MainLayoutProps {
  children: ReactNode
}

export default function MainLayout({ children }: MainLayoutProps) {
  return (
    <div className="app-container">
      <Sidebar />
      <main id="main" className="container-fluid px-md-4 py-md-3 px-2 py-2">
        <div className="row">
          <div className="col-12">
            {children}
          </div>
        </div>
      </main>
    </div>
  )
}
