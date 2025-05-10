"use client";

import "@/styles/common.css"
import { motion } from "framer-motion";
import { fadeInUp } from "@/utils/animations";
import { cn } from "@/utils/cn";
import { FiChevronRight } from "react-icons/fi";

// Importamos dinamicamente los iconos
import * as FaIcons from "react-icons/fa";
import * as FiIcons from "react-icons/fi";

interface PageHeaderProps {
  title: string;
  icon: string;
  subtitle?: string;
  breadcrumbs?: { label: string; href?: string }[];
}

export default function PageHeader({ title, icon, subtitle, breadcrumbs }: PageHeaderProps) {
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
      return IconComponent ? <IconComponent className="me-2" color="var(--primary)" /> : null;
    } else if (iconName.startsWith("fi")) {
      const key = iconName.replace(/^fi-/, "");
      const pascalKey = key
        .split("-")
        .map((part) => part.charAt(0).toUpperCase() + part.slice(1))
        .join("");
      
      // @ts-ignore - Sabemos que este índice dinámico es válido
      const IconComponent = FiIcons[`Fi${pascalKey}`];
      return IconComponent ? <IconComponent className="me-2" color="var(--primary)" /> : null;
    }
    
    // Si no podemos encontrar el icono, usamos el enfoque anterior
    return <i className={`${icon} me-2`} style={{ color: 'var(--primary)' }}></i>;
  };

  return (
    <motion.div 
      className="page-header-container mb-3 mb-md-4"
      initial="initial"
      animate="animate"
      exit="exit"
      variants={fadeInUp}
    >
      {breadcrumbs && breadcrumbs.length > 0 && (
        <div className="breadcrumb-container mb-2">
          <nav aria-label="breadcrumb">
            <ol className="breadcrumb mb-0">
              {breadcrumbs.map((crumb, index) => (
                <li 
                  key={`breadcrumb-${index}`} 
                  className={cn("breadcrumb-item", { "active": index === breadcrumbs.length - 1 })}
                >
                  {index === breadcrumbs.length - 1 ? (
                    crumb.label
                  ) : (
                    <>
                      <a href={crumb.href || "#"} className="breadcrumb-link">{crumb.label}</a>
                      <FiChevronRight className="breadcrumb-separator mx-1" size={14} />
                    </>
                  )}
                </li>
              ))}
            </ol>
          </nav>
        </div>
      )}
      
      <div className="d-flex flex-column flex-md-row align-items-md-center">
        <motion.h2 
          className="page-header fs-3 fs-md-2 mb-1 mb-md-0"
          initial={{ opacity: 0, y: 10 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ delay: 0.1 }}
        >
          {getIcon(icon)}
          {title}
        </motion.h2>
      </div>
      
      {subtitle && (
        <motion.p 
          className="page-subtitle text-muted mt-1 mt-md-2 fs-6"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ delay: 0.2 }}
        >
          {subtitle}
        </motion.p>
      )}
    </motion.div>
  )
}
