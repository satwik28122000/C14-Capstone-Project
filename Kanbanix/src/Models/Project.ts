import { Task } from "./task";

export type Project = {
    projectId?: string,
    projectName?: string,
    projectDesc?: string,
    projectTasks?: Task[] 
}