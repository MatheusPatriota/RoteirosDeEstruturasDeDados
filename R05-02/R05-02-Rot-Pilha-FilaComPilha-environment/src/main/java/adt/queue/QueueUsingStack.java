package adt.queue;

import adt.stack.Stack;
import adt.stack.StackImpl;
import adt.stack.StackOverflowException;

public class QueueUsingStack<T> implements Queue<T> {

   private Stack<T> stack1;
   private Stack<T> stack2;

   public QueueUsingStack(int size) {
      stack1 = new StackImpl<T>(size);
      stack2 = new StackImpl<T>(size);
   }

   @Override
   public void enqueue(T element) throws QueueOverflowException {
      if (isFull()) {
         throw new QueueOverflowException();
      }
      try {
         this.stack1.push(element);
      } catch (StackOverflowException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   @Override
   public T dequeue() throws QueueUnderflowException {
      T elementoExibido;
      try {
         while (!isEmpty()) {

            stack2.push(stack1.pop());
         }
         elementoExibido = stack2.pop();
         while (!isEmpty()) {
            stack1.push(stack2.pop());
         }

      } catch (Exception e) {
         throw new QueueUnderflowException();
      }
      return elementoExibido;
   }

   @Override
   public T head() {
      T elementoExibido = null;
      try {
         while (!isEmpty()) {

            stack2.push(stack1.pop());
         }
         elementoExibido = stack2.top();
         while (!isEmpty()) {
            stack1.push(stack2.pop());
         }

      } catch (Exception e) {

      }
      return elementoExibido;
   }

   @Override
   public boolean isEmpty() {
      return stack1.isEmpty();
   }

   @Override
   public boolean isFull() {
      return stack1.isFull();
   }

}
