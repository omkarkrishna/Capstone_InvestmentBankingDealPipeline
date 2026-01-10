import { useEffect, useState } from "react";
import { Card, Typography, Box, Divider } from "@mui/material";
import api from "../api/api";

const Analytics = () => {
  const [deals, setDeals] = useState<any[]>([]);
  const role = localStorage.getItem("role");

  useEffect(() => {
    api.get("/api/deals").then((res) => setDeals(res.data));
  }, []);

  /* ---------------- BASIC COUNTS ---------------- */
  const total = deals.length;
  const prospect = deals.filter((d) => d.stage === "PROSPECT").length;
  const underEval = deals.filter((d) => d.stage === "UNDER_EVALUATION").length;
  const termSheet = deals.filter(
    (d) => d.stage === "TERMSHEET_SUBMITTED"
  ).length;
  const closed = deals.filter((d) => d.stage === "CLOSED").length;
  const lost = deals.filter((d) => d.stage === "LOST").length;

  /* ---------------- DEAL AGING ---------------- */
  const now = new Date().getTime();

  const daysOld = (date: string) =>
    Math.floor((now - new Date(date).getTime()) / (1000 * 60 * 60 * 24));

  const recentDeals = deals.filter((d) => daysOld(d.updatedAt) <= 7).length;
  const agingDeals = deals.filter(
    (d) => daysOld(d.updatedAt) > 7 && daysOld(d.updatedAt) <= 30
  ).length;
  const staleDeals = deals.filter((d) => daysOld(d.updatedAt) > 30).length;

  /* ---------------- VALUE AGGREGATES (ADMIN ONLY) ---------------- */
  const pipelineValue = deals
    .filter(
      (d) =>
        d.dealValue != null &&
        ["PROSPECT", "UNDER_EVALUATION", "TERMSHEET_SUBMITTED"].includes(d.stage)
    )
    .reduce((sum, d) => sum + d.dealValue, 0);

  const closedValue = deals
    .filter((d) => d.dealValue != null && d.stage === "CLOSED")
    .reduce((sum, d) => sum + d.dealValue, 0);

  const lostValue = deals
    .filter((d) => d.dealValue != null && d.stage === "LOST")
    .reduce((sum, d) => sum + d.dealValue, 0);

  const percent = (count: number) =>
    total === 0 ? 0 : Math.round((count / total) * 100);

  return (
    <div className="app-container">
      <Typography variant="h4" fontWeight={600} gutterBottom>
        Analytics Dashboard
      </Typography>

      {/* ---------------- KPI CARDS ---------------- */}
      <Box sx={{ display: "flex", gap: 2, mb: 4 }}>
        <Card sx={{ flex: 1, p: 3, borderRadius: 2, boxShadow: 2 }}>
          <Typography variant="body2" color="text.secondary">
            Total Deals
          </Typography>
          <Typography variant="h4" fontWeight={600}>
            {total}
          </Typography>
        </Card>

        <Card
          sx={{
            flex: 1,
            p: 3,
            borderRadius: 2,
            boxShadow: 2,
            color: "green",
          }}
        >
          <Typography variant="body2" color="text.secondary">
            Closed Deals
          </Typography>
          <Typography variant="h4" fontWeight={600}>
            {closed}
          </Typography>
        </Card>

        <Card
          sx={{
            flex: 1,
            p: 3,
            borderRadius: 2,
            boxShadow: 2,
            color: "red",
          }}
        >
          <Typography variant="body2" color="text.secondary">
            Lost Deals
          </Typography>
          <Typography variant="h4" fontWeight={600}>
            {lost}
          </Typography>
        </Card>
      </Box>

      {/* ---------------- STAGE DISTRIBUTION ---------------- */}
      <Card sx={{ p: 3, borderRadius: 2, boxShadow: 2, mb: 4 }}>
        <Typography variant="h6" fontWeight={600} gutterBottom>
          Deal Stage Distribution
        </Typography>

        {[
          { label: "Prospect", value: prospect, color: "#1976d2" },
          { label: "Under Evaluation", value: underEval, color: "#9c27b0" },
          {
            label: "Term Sheet Submitted",
            value: termSheet,
            color: "#ff9800",
          },
          { label: "Closed", value: closed, color: "green" },
          { label: "Lost", value: lost, color: "red" },
        ].map((item) => (
          <Box key={item.label} sx={{ mb: 2 }}>
            <Box sx={{ display: "flex", justifyContent: "space-between" }}>
              <Typography variant="body2">{item.label}</Typography>
              <Typography variant="body2" color="text.secondary">
                {item.value}
              </Typography>
            </Box>

            <Box
              sx={{
                height: 12,
                backgroundColor: "#e0e0e0",
                borderRadius: 6,
                overflow: "hidden",
              }}
            >
              <Box
                sx={{
                  height: "100%",
                  width: `${percent(item.value)}%`,
                  backgroundColor: item.color,
                }}
              />
            </Box>
          </Box>
        ))}
      </Card>

      {/* ---------------- DEAL AGING ---------------- */}
      <Card sx={{ p: 3, borderRadius: 2, boxShadow: 2, mb: 4 }}>
        <Typography variant="h6" fontWeight={600} gutterBottom>
          Deal Aging
        </Typography>

        <Typography>Updated in last 7 days: {recentDeals}</Typography>
        <Typography>Updated in 7–30 days: {agingDeals}</Typography>
        <Typography color="error">
          Stale deals (30+ days): {staleDeals}
        </Typography>
      </Card>

      {/* ---------------- VALUE METRICS (ADMIN ONLY) ---------------- */}
      {role === "ADMIN" && (
        <Card sx={{ p: 3, borderRadius: 2, boxShadow: 2 }}>
          <Typography variant="h6" fontWeight={600} gutterBottom>
            Valuation Overview (Admin Only)
          </Typography>

          <Divider sx={{ my: 1 }} />

          <Typography>Total Pipeline Value: ₹ {pipelineValue}</Typography>
          <Typography color="green">
            Closed Deal Value: ₹ {closedValue}
          </Typography>
          <Typography color="red">
            Lost Deal Value: ₹ {lostValue}
          </Typography>
        </Card>
      )}
    </div>
  );
};

export default Analytics;
