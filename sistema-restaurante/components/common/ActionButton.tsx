"use client"

import type { ReactNode } from "react"

interface ActionButtonProps {
  icon: string
  variant?: string
  size?: "sm" | "md" | "lg"
  children: ReactNode
  onClick?: () => void
  className?: string
  hideTextOnMobile?: boolean
}

export default function ActionButton({ 
  icon, 
  variant = "primary", 
  size = "md", 
  children, 
  onClick, 
  className = "",
  hideTextOnMobile = false
}: ActionButtonProps) {
  const sizeClass = size === "sm" ? "btn-sm" : size === "lg" ? "btn-lg" : ""

  return (
    <button 
      className={`btn btn-${variant} ${sizeClass} ${className} d-flex align-items-center justify-content-center`} 
      onClick={onClick}
    >
      <i className={`${icon} ${hideTextOnMobile ? "me-md-2" : "me-2"}`}></i>
      {hideTextOnMobile ? (
        <span className="d-none d-md-inline">{children}</span>
      ) : (
        children
      )}
    </button>
  )
}
