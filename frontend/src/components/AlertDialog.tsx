import React from "react";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogContentText,
  DialogActions,
  Button
} from "@mui/material";
import { AlertDialogProps } from "../types";


export default function AlertDialog({
  open,
  title,
  message,
  onConfirm,
  onCancel,
}: AlertDialogProps) {

  return (
    <Dialog open={open} onClose={onCancel}>
      <DialogTitle>{title}</DialogTitle>
      <DialogContent>
        <DialogContentText>{message}</DialogContentText>
      </DialogContent>
      <DialogActions>
        <Button onClick={onCancel} color="primary">
          cancel
        </Button>
        <Button onClick={onConfirm} color="primary" autoFocus>
          ok
        </Button>
      </DialogActions>
    </Dialog>
  );
}