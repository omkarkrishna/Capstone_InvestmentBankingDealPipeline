import { Typography } from "@mui/material";

const ActivityTimeline = ({ activities }: any) => (
  <>
    <Typography variant="h6">Activity Timeline</Typography>
    {activities.map((a: any, i: number) => (
      <Typography key={i}>
        {a.timestamp} — {a.message}
      </Typography>
    ))}
  </>
);

export default ActivityTimeline;
