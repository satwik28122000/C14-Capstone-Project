import { HttpInterceptorFn } from '@angular/common/http';

export const customInterceptor: HttpInterceptorFn = (req, next) => {
  const token = localStorage.getItem("token");
    
  req = req.clone({headers: req.headers.set('Authorization',`Bearer ${token}`)});
  console.log('Interceptor called');
  return next(req);
};
