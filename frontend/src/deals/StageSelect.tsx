import { Select, MenuItem } from "@mui/material";
import api from "../api/api";

const StageSelect = ({ deal, onUpdate }: any) => {
  const updateStage = async (newStage: string) => {
    await api.patch(`/api/deals/${deal.id}/stage`, { stage: newStage });
    const updated = await api.get(`/api/deals/${deal.id}`);
    onUpdate(updated.data);
  };

  return (
    <Select
      value={deal.stage}
      onChange={(e) => updateStage(e.target.value)}
      size="small"
    >
      <MenuItem value="PROSPECT">Prospect</MenuItem>
      <MenuItem value="UNDER_EVALUATION">Under Evaluation</MenuItem>
      <MenuItem value="TERMSHEET_SUBMITTED">Term Sheet Submitted</MenuItem>
      <MenuItem value="CLOSED">Closed</MenuItem>
      <MenuItem value="LOST">Lost</MenuItem>
    </Select>
  );
};

export default StageSelect;
