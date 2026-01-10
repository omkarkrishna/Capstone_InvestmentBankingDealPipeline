import { Card, Typography, Box } from "@mui/material";
import { useNavigate } from "react-router-dom";

export type Deal = {
  id: string;
  clientName: string;
  dealType: string;
  sector: string;
  dealValue?: number | null;
  stage: string;
};

const DealCard = ({ deal }: { deal: Deal }) => {
  const navigate = useNavigate();
  const role = localStorage.getItem("role");

  return (
    <Card
      onClick={() => navigate(`/deals/${deal.id}`)}
      sx={{
        p: 2,
        mb: 1.5,
        cursor: "pointer",
        borderRadius: 2,
        borderLeft: "5px solid #1976d2", 
        transition: "0.2s",
        "&:hover": {
          boxShadow: "0 6px 16px rgba(0,0,0,0.15)",
          transform: "translateY(-2px)",
        },
      }}
    >
      <Typography fontWeight={600}>{deal.clientName}</Typography>

      <Typography variant="body2" color="text.secondary">
        {deal.dealType} • {deal.sector}
      </Typography>

      {role === "ADMIN" && deal.dealValue != null && (
        <Box mt={1}>
          <Typography fontWeight={600} color="success.main">
            ₹ {deal.dealValue}
          </Typography>
        </Box>
      )}
    </Card>
  );
};

export default DealCard;
