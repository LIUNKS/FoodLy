import { Metadata } from "next"

export const metadata: Metadata = {
  title: "FoodLy - Login",
}

export default function AuthLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return <>{children}</>
}