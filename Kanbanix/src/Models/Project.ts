import { Task } from "./Task";

export class Project {
    projectId?: string;
    projectName?: string;
    projectDesc?: string;
    projectTasks?: Task[]; 
}