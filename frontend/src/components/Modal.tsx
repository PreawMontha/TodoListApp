import React, { useEffect, useState } from "react";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  TextField,
  MenuItem,
  Select,
  FormControl,
  Typography,
  Box,
  IconButton,
} from "@mui/material";
import CloseIcon from '@mui/icons-material/Close';
import { AddModalProps } from "../types";


export default function AddModal({ open, onClose, onSubmit, formData  }: AddModalProps) {
  
  const [title, setTitle] = useState("");
  const [status, setStatus] = useState(1);
  const [detail, setDetail] = useState("");
  const [priority, setPriority] = useState<number>(1);

  const priorityLevels = [
    { label: "Low", value: 1 },
    { label: "Medium", value: 2 },
    { label: "High", value: 3 },
  ];

  useEffect(() => {
    if (formData) {
      setTitle(formData.title || "");
      setDetail(formData.description || "");
      setStatus(formData.status ?? 1);
      setPriority(formData.priority ?? 1);
    }
  }, [formData]);

const handleSubmit = (e: React.FormEvent) => {
  e.preventDefault();
  onSubmit({ 
    title, 
    detail, 
    status, 
    priority: priority,
  });
  setTitle("");
  setDetail("");
  setStatus(1);
  onClose();
};

  return (
    <Dialog open={open} onClose={onClose} fullWidth maxWidth="sm">
      <DialogTitle sx={{ display: "flex", alignItems: "center", justifyContent: "space-between", pr: 1 }}>
        <TextField
          autoFocus
          fullWidth
          variant="standard"
          placeholder="Title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
        />
        <IconButton
          aria-label="close"
          onClick={onClose}
          sx={{ ml: 2 }}
        >
          <CloseIcon />
        </IconButton>
      </DialogTitle>

      <DialogContent>
        <form onSubmit={handleSubmit}>
          <Box display="flex" justifyContent="space-between" alignItems="center" mb={1}>
            <Typography variant="body2" pl={1}>
              Description
            </Typography>
           
          </Box>

          <TextField
            placeholder="Detail"
            multiline
            rows={4}
            fullWidth
            variant="outlined"
            value={detail}
            onChange={(e) => setDetail(e.target.value)}
            sx={{ mb: 3 }}
          />

          <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>

                    <Box sx={{ mb: 3 }}>
                      <Typography variant="body2" mb={1}>
                        Priority
                      </Typography>
                      <Box sx={{ display: "flex", gap: 1 }}>
                        {priorityLevels.map(({ label, value }) => (
                          <Button
                            key={label}
                            variant={priority === value ? "contained" : "outlined"}
                            color={
                              value === 1 ? "primary" : value === 2 ? "warning" : "error"
                            }
                            size="small"
                            onClick={() => {
                        console.log("Clicked priority:", value);
                        setPriority(value);
                      }}
                    sx={{
                      textTransform: "none",
                      opacity: priority === value ? 1 : 0.5,
                      minWidth: 80,
                      borderRadius: "100px",
                    }}
                  >
                    {label}
                  </Button>
                ))}
              </Box>
            </Box>
                      
                      <FormControl variant="standard" sx={{ minWidth: 200 }}>
               <Typography variant="body2" >
              Status
            </Typography>
          <Select
            value={status}
            onChange={(e) => setStatus(Number(e.target.value))}
            sx={{
              fontWeight: "bold",
              color: status === 1 ? "green" : "red",
              // textDecoration: "underline",
              userSelect: "none",
              cursor: "pointer",
            }}
          >
            <MenuItem value={1} sx={{ color: "green" }}>
              Active
            </MenuItem>
            <MenuItem value={0} sx={{ color: "red" }}>
              Inactive
            </MenuItem>
          </Select>
        </FormControl>
          </Box>

          <DialogActions sx={{ px: 0 }}>
            <Button onClick={onClose} variant="outlined">
              cancel
            </Button>
            <Button type="submit" variant="contained" color="primary" disabled={!title}>
              {formData ? "submit" : "submit"}
           </Button>
          </DialogActions>
        </form>
      </DialogContent>
    </Dialog>
  );
}
