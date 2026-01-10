import { Button, Dialog, DialogTitle, DialogContent, TextField } from "@mui/material";
import { useState } from "react";
import api from "../api/api";

const CreateDealDialog = () => {
  const [open, setOpen] = useState(false);
  const [clientName, setClientName] = useState("");
  const [dealType, setDealType] = useState("");
  const [sector, setSector] = useState("");
  const [summary, setSummary] = useState("");

  const createDeal = async () => {
    await api.post("/api/deals", {
      clientName,
      dealType,
      sector,
      summary,
    });
    window.location.reload();
  };

  return (
    <>
      <Button variant="contained" onClick={() => setOpen(true)}>
        Create Deal
      </Button>

      <Dialog open={open} onClose={() => setOpen(false)} fullWidth>
        <DialogTitle>Create Deal</DialogTitle>
        <DialogContent>
          <TextField label="Client Name" fullWidth margin="normal" onChange={(e) => setClientName(e.target.value)} />
          <TextField label="Deal Type" fullWidth margin="normal" onChange={(e) => setDealType(e.target.value)} />
          <TextField label="Sector" fullWidth margin="normal" onChange={(e) => setSector(e.target.value)} />
          <TextField label="Summary" fullWidth margin="normal" onChange={(e) => setSummary(e.target.value)} />
          <Button sx={{ mt: 2 }} variant="contained" onClick={createDeal}>
            Save
          </Button>
        </DialogContent>
      </Dialog>
    </>
  );
};

export default CreateDealDialog;
