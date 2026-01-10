import { Button, TextField } from "@mui/material";
import { useState } from "react";
import api from "../api/api";

const EditDealValueDialog = ({ deal }: any) => {
  const [value, setValue] = useState(deal.dealValue || "");

  const save = async () => {
    await api.patch(`/api/deals/${deal.id}/value`, {
      dealValue: value,
    });
    window.location.reload();
  };

  return (
    <>
      <TextField
        size="small"
        value={value}
        onChange={(e) => setValue(e.target.value)}
      />
      <Button onClick={save}>Save</Button>
    </>
  );
};

export default EditDealValueDialog;
