import { Employee } from "./Employee";


export type Task = {
    taskId?: string;
    taskName?: string;
    taskDesc?: string;
    status?: string;
    priority?: string;
    dueDate?: string;
    assignedTo?: Employee;
    projectId?: string;
}