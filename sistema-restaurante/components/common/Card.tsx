"use client";

import { ReactNode } from "react";
import { motion } from "framer-motion";
import { cn } from "@/utils/cn";
import * as FaIcons from "react-icons/fa";
import * as FiIcons from "react-icons/fi";

interface CardProps {
  title: string;
  icon: string;
  headerClass?: string;
  children: ReactNode;
  className?: string;
  footer?: ReactNode;
  loading?: boolean;
  animate?: boolean;
}

export default function Card({ 
  title, 
  icon, 
  headerClass = "bg-primary", 
  children, 
  className = "",
  footer,
  loading = false,
  animate = true
}: CardProps) {
  // Determinamos qué conjunto de iconos usar
  const getIcon = (iconName: string) => {
    if (iconName.startsWith("fa")) {
      const key = iconName.replace(/^fa-/, "");
      // Convertimos kebab-case a PascalCase para nombres de componentes
      const pascalKey = key
        .split("-")
        .map((part) => part.charAt(0).toUpperCase() + part.slice(1))
        .join("");
      
      // @ts-ignore - Sabemos que este índice dinámico es válido
      const IconComponent = FaIcons[`Fa${pascalKey}`];
      return IconComponent ? <IconComponent className="me-2" /> : null;
    } else if (iconName.startsWith("fi")) {
      const key = iconName.replace(/^fi-/, "");
      const pascalKey = key
        .split("-")
        .map((part) => part.charAt(0).toUpperCase() + part.slice(1))
        .join("");
      
      // @ts-ignore - Sabemos que este índice dinámico es válido
      const IconComponent = FiIcons[`Fi${pascalKey}`];
      return IconComponent ? <IconComponent className="me-2" /> : null;
    }
    
    // Si no podemos encontrar el icono, usamos el enfoque anterior
    return <i className={`${icon} me-2`}></i>;
  };

  const cardVariants = {
    hidden: { opacity: 0, y: 20 },
    visible: { 
      opacity: 1, 
      y: 0,
      transition: { duration: 0.4 }
    }
  };

  const CardComponent = animate ? motion.div : "div";

  return (
    <CardComponent
      className={cn("card mb-4 shadow-sm rounded-3 overflow-hidden", className)}
      variants={animate ? cardVariants : undefined}
      initial={animate ? "hidden" : undefined}
      animate={animate ? "visible" : undefined}
    >
      <div className={`card-header ${headerClass} text-white p-2 p-md-3`}>
        <h5 className="mb-0 fs-5 d-flex align-items-center">
          {getIcon(icon)} {title}
        </h5>
      </div>
      
      <div className="card-body p-3 p-md-4 position-relative">
        {loading ? (
          <div className="text-center py-5">
            <div className="spinner-border text-primary" role="status">
              <span className="visually-hidden">Cargando...</span>
            </div>
            <p className="mt-3 text-muted">Cargando datos...</p>
          </div>
        ) : (
          children
        )}
      </div>

      {footer && (
        <div className="card-footer bg-light p-3">
          {footer}
        </div>
      )}
    </CardComponent>
  );
}
