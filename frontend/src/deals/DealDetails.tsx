import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Card, Typography, Divider, Box } from "@mui/material";
import api from "../api/api";
import StageSelect from "./StageSelect";
import NotesSection from "../notes/NotesSection";
import ActivityTimeline from "../timeline/ActivityTimeline";
import EditDealValueDialog from "./EditDealValueDialog";
import EditDealDialog from "./EditDealDialog";
import DeleteDealButton from "./DeleteDealButton";

const DealDetails = () => {
  const { id } = useParams();
  const [deal, setDeal] = useState<any>(null);

  const role = localStorage.getItem("role");

  useEffect(() => {
    api.get(`/api/deals/${id}`).then((res) => setDeal(res.data));
  }, [id]);

  if (!deal) return <div>Loading...</div>;

  return (
    <div className="app-container">
      <Typography variant="h4" gutterBottom>
        {deal.clientName}
      </Typography>

      {/* BASIC DEAL INFO */}
      <Card sx={{ p: 2, mb: 2 }}>
        <Typography>Deal Type: {deal.dealType}</Typography>
        <Typography>Sector: {deal.sector}</Typography>

        <Divider sx={{ my: 1 }} />

        <Typography variant="subtitle2">Stage</Typography>
        <StageSelect deal={deal} onUpdate={setDeal} />

        {/* EDIT DEAL (USER + ADMIN) */}
        <Box sx={{ mt: 2 }}>
          <EditDealDialog deal={deal} />
        </Box>

        {/* ADMIN ONLY: DEAL VALUE */}
        {role === "ADMIN" && (
          <>
            <Divider sx={{ my: 2 }} />
            <Typography variant="subtitle2">
              Deal Value (Admin Only)
            </Typography>
            <EditDealValueDialog deal={deal} />
          </>
        )}

        {/* ADMIN ONLY: DELETE DEAL */}
        {role === "ADMIN" && (
          <Box sx={{ mt: 2 }}>
            <DeleteDealButton dealId={deal.id} />
          </Box>
        )}
      </Card>

      {/* NOTES */}
      <Card sx={{ p: 2, mb: 2 }}>
        <NotesSection dealId={deal.id} notes={deal.notes} />
      </Card>

      {/* ACTIVITY TIMELINE */}
      <Card sx={{ p: 2 }}>
        <ActivityTimeline activities={deal.activities} />
      </Card>
    </div>
  );
};

export default DealDetails;
