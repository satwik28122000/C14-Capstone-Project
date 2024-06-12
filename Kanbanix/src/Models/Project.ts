import { Task } from "./task";


export class Project {
    projectId?: string;
    projectName?: string;
    projectDesc?: string;
    projectTasks?: Task[]; 
}