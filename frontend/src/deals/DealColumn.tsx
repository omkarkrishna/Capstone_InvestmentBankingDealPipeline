import { Card, Typography, Chip } from "@mui/material";
import DealCard from "./DealCard";
import type { Deal } from "./DealCard"; 

const DealColumn = ({
  title,
  stageKey: _stageKey, 
  deals,
}: {
  title: string;
  stageKey: string;
  deals: Deal[];
}) => {
  return (
    <Card sx={{  width: 280,
    p: 2,
    background: "#ffffff",
    borderRadius: 2,
    boxShadow: "0 4px 12px rgba(0,0,0,0.08)", }}>
      <Typography variant="h6">
        {title} <Chip size="small" label={deals.length} />
      </Typography>

      {deals.length === 0 && (
        <Typography variant="body2" color="text.secondary">
          No deals
        </Typography>
      )}

      {deals.map((d) => (
        <DealCard key={d.id} deal={d} />
      ))}
    </Card>
  );
};

export default DealColumn;
