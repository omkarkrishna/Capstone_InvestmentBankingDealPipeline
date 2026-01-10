import { BrowserRouter, Routes, Route, useLocation } from "react-router-dom";
import Login from "./auth/Login";
import DealBoard from "./deals/DealBoard";
import DealDetails from "./deals/DealDetails";
import Analytics from "./analytics/Analytics";
import UserManagement from "./admin/UserManagement";
import PrivateRoute from "./routes/PrivateRoute";
import RoleRoute from "./routes/RoleRoute";
import Navbar from "./layout/Navbar";

const AppLayout = () => {
  const location = useLocation();
  const hideNavbar = location.pathname === "/login";

  return (
    <>
      {!hideNavbar && <Navbar />}
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/" element={<PrivateRoute><DealBoard /></PrivateRoute>} />
        <Route path="/deals/:id" element={<PrivateRoute><DealDetails /></PrivateRoute>} />
        <Route path="/analytics" element={<PrivateRoute><Analytics /></PrivateRoute>} />
        <Route
          path="/admin/users"
          element={
            <PrivateRoute>
              <RoleRoute role="ADMIN">
                <UserManagement />
              </RoleRoute>
            </PrivateRoute>
          }
        />
      </Routes>
    </>
  );
};

function App() {
  return (
    <BrowserRouter>
      <AppLayout />
    </BrowserRouter>
  );
}

export default App;
