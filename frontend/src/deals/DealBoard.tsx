import { useEffect, useState } from "react";
import { Typography, Card, Box } from "@mui/material";
import api from "../api/api";
import DealColumn from "./DealColumn";
import CreateDealDialog from "./CreateDealDialog";
import type { Deal } from "./DealCard";

const STAGES = [
  { key: "PROSPECT", label: "Prospect" },
  { key: "UNDER_EVALUATION", label: "Under Evaluation" },
  { key: "TERMSHEET_SUBMITTED", label: "Term Sheet Submitted" },
  { key: "CLOSED", label: "Closed" },
  { key: "LOST", label: "Lost" },
];

const DealBoard = () => {
  const [deals, setDeals] = useState<Deal[]>([]);

  useEffect(() => {
    api.get("/api/deals").then((res) => setDeals(res.data));
  }, []);

  const closed = deals.filter((d) => d.stage === "CLOSED").length;
  const lost = deals.filter((d) => d.stage === "LOST").length;

  return (
    <div className="app-container">
      {/* HEADER */}
      <Box
        sx={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          mb: 3,
        }}
      >
        <Typography variant="h4" fontWeight={600}>
          Deal Pipelin
        </Typography>
        <CreateDealDialog />
      </Box>

      {/* SUMMARY DASHBOARD */}
      <Box sx={{ display: "flex", gap: 2, mb: 4 }}>
        <Card
          sx={{
            p: 2.5,
            flex: 1,
            borderRadius: 2,
            boxShadow: "0 4px 12px rgba(0,0,0,0.08)",
          }}
        >
          <Typography variant="body2" color="text.secondary">
            Total Deals
          </Typography>
          <Typography variant="h5" fontWeight={600}>
            {deals.length}
          </Typography>
        </Card>

        <Card
          sx={{
            p: 2.5,
            flex: 1,
            borderRadius: 2,
            boxShadow: "0 4px 12px rgba(0,0,0,0.08)",
            color: "green",
          }}
        >
          <Typography variant="body2" color="text.secondary">
            Closed Deals
          </Typography>
          <Typography variant="h5" fontWeight={600}>
            {closed}
          </Typography>
        </Card>

        <Card
          sx={{
            p: 2.5,
            flex: 1,
            borderRadius: 2,
            boxShadow: "0 4px 12px rgba(0,0,0,0.08)",
            color: "red",
          }}
        >
          <Typography variant="body2" color="text.secondary">
            Lost Deals
          </Typography>
          <Typography variant="h5" fontWeight={600}>
            {lost}
          </Typography>
        </Card>
      </Box>

      {/* KANBAN BOARD */}
      <Box
        sx={{
          display: "flex",
          gap: 2,
          overflowX: "auto",
          pb: 2,
        }}
      >
        {STAGES.map((s) => (
          <DealColumn
            key={s.key}
            stageKey={s.key}
            title={s.label}
            deals={deals.filter((d) => d.stage === s.key)}
          />
        ))}
      </Box>
    </div>
  );
};

export default DealBoard;
