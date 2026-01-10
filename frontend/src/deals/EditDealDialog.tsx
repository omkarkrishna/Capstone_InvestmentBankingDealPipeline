import { Button, Dialog, DialogTitle, DialogContent, TextField } from "@mui/material";
import { useState } from "react";
import api from "../api/api";

const EditDealDialog = ({ deal }: any) => {
  const [open, setOpen] = useState(false);
  const [summary, setSummary] = useState(deal.summary);
  const [dealType, setDealType] = useState(deal.dealType);
  const [sector, setSector] = useState(deal.sector);

  const updateDeal = async () => {
    await api.put(`/api/deals/${deal.id}`, {
      summary,
      dealType,
      sector,
    });
    window.location.reload();
  };

  return (
    <>
      <Button size="small" onClick={() => setOpen(true)}>
        Edit Deal
      </Button>

      <Dialog open={open} onClose={() => setOpen(false)} fullWidth>
        <DialogTitle>Edit Deal</DialogTitle>
        <DialogContent>
          <TextField label="Deal Type" fullWidth margin="normal" value={dealType} onChange={(e) => setDealType(e.target.value)} />
          <TextField label="Sector" fullWidth margin="normal" value={sector} onChange={(e) => setSector(e.target.value)} />
          <TextField label="Summary" fullWidth margin="normal" value={summary} onChange={(e) => setSummary(e.target.value)} />
          <Button sx={{ mt: 2 }} variant="contained" onClick={updateDeal}>
            Update
          </Button>
        </DialogContent>
      </Dialog>
    </>
  );
};

export default EditDealDialog;
