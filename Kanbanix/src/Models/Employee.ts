import { Task } from "./Task";

export class Employee {
    userId?: string;
    userName?: string;
    password?: string;
    designation?: string;
    emailId?: string;
    managerId?: string;
    userTaskList?: Task[]; 