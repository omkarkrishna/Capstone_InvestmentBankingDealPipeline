import { Navigate } from "react-router-dom";
import React from "react";

const RoleRoute = ({
  children,
  role,
}: {
  children: React.ReactNode;
  role: string;
}) => {
  const userRole = localStorage.getItem("role");
  return userRole === role ? <>{children}</> : <Navigate to="/" />;
};

export default RoleRoute;
