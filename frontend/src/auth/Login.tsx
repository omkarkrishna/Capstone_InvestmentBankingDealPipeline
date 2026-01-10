import { useState } from "react";
import { Button, TextField, Typography, Card, Box } from "@mui/material";
import api from "../api/api";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const login = async () => {
    try {
      const res = await api.post("/api/auth/login", {
        username,
        password,
      });

      localStorage.setItem("token", res.data.token);
      localStorage.setItem("role", res.data.role);

      navigate("/");
    } catch {
      alert("Invalid credentials");
    }
  };

  return (
    <Box
      sx={{
        height: "100vh",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        backgroundColor: "#f4f6f8",
      }}
    >
      <Card
        sx={{
          p: 4,
          width: 360,
          borderRadius: 2,
          boxShadow: "0 8px 24px rgba(0,0,0,0.15)",
        }}
      >
        <Typography variant="h5" fontWeight={600} gutterBottom>
          Investment Banking Portal
        </Typography>

        <TextField
          fullWidth
          label="Username"
          margin="normal"
          onChange={(e) => setUsername(e.target.value)}
        />

        <TextField
          fullWidth
          label="Password"
          type="password"
          margin="normal"
          onChange={(e) => setPassword(e.target.value)}
        />

        <Button
          fullWidth
          variant="contained"
          sx={{ mt: 2 }}
          onClick={login}
        >
          Login
        </Button>
      </Card>
    </Box>
  );
};

export default Login;
