import { AppBar, Toolbar, Button, Typography } from "@mui/material";
import { useNavigate } from "react-router-dom";

const Navbar = () => {
  const navigate = useNavigate();
  const role = localStorage.getItem("role");

  const logout = () => {
    localStorage.clear();
    navigate("/login");
  };

  return (
    <AppBar position="static">
      <Toolbar>
        <Typography sx={{ flexGrow: 1 }}>
          Investment Banking Portal
        </Typography>

        <Button color="inherit" onClick={() => navigate("/")}>
          Pipeline
        </Button>

        <Button color="inherit" onClick={() => navigate("/analytics")}>
          Analytics
        </Button>

        {role === "ADMIN" && (
          <Button color="inherit" onClick={() => navigate("/admin/users")}>
            Users
          </Button>
        )}

        <Button color="inherit" onClick={logout}>
          Logout
        </Button>
      </Toolbar>
    </AppBar>
  );
};

export default Navbar;
