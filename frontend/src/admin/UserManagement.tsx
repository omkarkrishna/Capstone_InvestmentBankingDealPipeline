import { useEffect, useState } from "react";
import {
  Card,
  Typography,
  Button,
  Chip,
  Box,
  Divider,
} from "@mui/material";
import api from "../api/api";

const UserManagement = () => {
  const [users, setUsers] = useState<any[]>([]);

  useEffect(() => {
    api.get("/api/admin/users").then((res) => setUsers(res.data));
  }, []);

  const toggleStatus = async (id: string, active: boolean) => {
    await api.put(`/api/admin/users/${id}/status`, {
      active: !active,
    });
    window.location.reload();
  };

  const activeUsers = users.filter((u) => u.active).length;
  const inactiveUsers = users.length - activeUsers;

  return (
    <div className="app-container">
      <Typography variant="h4" fontWeight={600} gutterBottom>
        User Management
      </Typography>

      {/* SUMMARY STRIP */}
      <Box sx={{ display: "flex", gap: 2, mb: 3 }}>
        <Card sx={{ flex: 1, p: 2 }}>
          <Typography variant="body2" color="text.secondary">
            Total Users
          </Typography>
          <Typography variant="h5" fontWeight={600}>
            {users.length}
          </Typography>
        </Card>

        <Card sx={{ flex: 1, p: 2 }}>
          <Typography variant="body2" color="text.secondary">
            Active Users
          </Typography>
          <Typography variant="h5" fontWeight={600} color="green">
            {activeUsers}
          </Typography>
        </Card>

        <Card sx={{ flex: 1, p: 2 }}>
          <Typography variant="body2" color="text.secondary">
            Inactive Users
          </Typography>
          <Typography variant="h5" fontWeight={600} color="gray">
            {inactiveUsers}
          </Typography>
        </Card>
      </Box>

      {/* USER CARDS */}
      {users.map((u) => (
        <Card
          key={u.id}
          sx={{
            p: 2.5,
            mb: 2,
            borderRadius: 2,
            borderLeft: "5px solid #1976d2",
            boxShadow: "0 4px 12px rgba(0,0,0,0.08)",
          }}
        >
          <Box
            sx={{
              display: "flex",
              justifyContent: "space-between",
              alignItems: "center",
            }}
          >
            {/* USER INFO */}
            <Box>
              <Typography fontWeight={600}>{u.username}</Typography>

              <Box sx={{ display: "flex", gap: 1, mt: 0.5 }}>
                <Chip size="small" label={u.role} />
                <Chip
                  size="small"
                  label={u.active ? "Active" : "Inactive"}
                  color={u.active ? "success" : "default"}
                />
              </Box>
            </Box>

            {/* ACTION */}
            <Button
              size="small"
              variant="outlined"
              color={u.active ? "error" : "success"}
              onClick={() => toggleStatus(u.id, u.active)}
            >
              {u.active ? "Deactivate" : "Activate"}
            </Button>
          </Box>

          <Divider sx={{ mt: 2 }} />
        </Card>
      ))}
    </div>
  );
};

export default UserManagement;
