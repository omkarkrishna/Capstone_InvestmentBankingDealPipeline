import { Button } from "@mui/material";
import api from "../api/api";

const DeleteDealButton = ({ dealId }: { dealId: string }) => {
  const role = localStorage.getItem("role");

  if (role !== "ADMIN") return null;

  const deleteDeal = async () => {
    if (!window.confirm("Are you sure you want to delete this deal?")) return;
    await api.delete(`/api/deals/${dealId}`);
    window.location.href = "/";
  };

  return (
    <Button
      color="error"
      variant="outlined"
      onClick={deleteDeal}   
    >
      Delete Deal
    </Button>
  );
};

export default DeleteDealButton;
