import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  devIndicators: false,
  // Desactivar el prefetching automático para páginas específicas
  experimental: {
    // Optimizar la estrategia de precarga para reducir advertencias
    optimizeCss: true
  }
};

export default nextConfig;
