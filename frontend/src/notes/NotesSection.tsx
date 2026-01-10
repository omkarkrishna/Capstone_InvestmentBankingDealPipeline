import { Button, TextField, Typography, Card } from "@mui/material";
import { useState } from "react";
import api from "../api/api";

const NotesSection = ({ dealId, notes }: any) => {
  const [note, setNote] = useState("");

  const addNote = async () => {
    if (!note.trim()) return;
    await api.post(`/api/deals/${dealId}/notes`, { note });
    window.location.reload();
  };

  return (
    <>
      <Typography variant="h6" gutterBottom>
        Notes
      </Typography>

      {notes.length === 0 && (
        <Typography color="text.secondary">No notes yet</Typography>
      )}

      {notes.map((n: any, i: number) => (
        <Card key={i} sx={{ p: 1.5, mb: 1, background: "#f5f7fa" }}>
          <Typography>{n.note}</Typography>
          <Typography variant="caption" color="text.secondary">
            Added by {n.addedBy} • {new Date(n.addedAt).toLocaleString()}
          </Typography>
        </Card>
      ))}

      <TextField
        fullWidth
        size="small"
        placeholder="Add a note"
        value={note}
        onChange={(e) => setNote(e.target.value)}
        sx={{ mt: 1 }}
      />
      <Button sx={{ mt: 1 }} variant="contained" onClick={addNote}>
        Add Note
      </Button>
    </>
  );
};

export default NotesSection;
