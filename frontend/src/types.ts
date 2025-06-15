// src/types.ts

export type AddModalProps = {
  open: boolean;
  onClose: () => void;
  onSubmit: (data: {
    title: string;
    detail: string;
    status: number;
    priority: number;
  }) => void;
  formData?: {
    id: number;
    title: string;
    description?: string;
    status: number;
    priority: number;
  } | null;
};

export type Card = {
  id: number;
  title: string;
  description?: string;
  priority: "Low" | "Medium" | "High";
  status: "Active" | "Inactive" | "Unknown";
  dueDate?: string;
  priorityDisplay?: string;
};

export interface CardListProps {
  cards: Card[];
  onPriorityChange?: (id: number, newPriority: Card["priority"]) => void;
  onEdit: (card: Card) => void;
  onDelete: (id: number) => void;
}

export interface AlertDialogProps {
  open: boolean;
  title: string;
  message: string;
  onConfirm: () => void;
  onCancel: () => void;
};

export interface ImageCarouselProps {
  images: string[];
  interval?: number; // optional, มี default = 3000
}