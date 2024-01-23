// Checkbox.tsx

import * as React from "react";
import { CheckIcon } from "@radix-ui/react-icons";
import * as CheckboxPrimitive from "@radix-ui/react-checkbox";
import { cn } from "@/lib/utils";

const Checkbox = CheckboxPrimitive.Root;

const CheckboxTrigger = React.forwardRef<
  React.ElementRef<typeof CheckboxPrimitive.Root>,
  React.ComponentPropsWithoutRef<typeof CheckboxPrimitive.Root>
>(({ className, children, ...props }, ref) => (
  <CheckboxPrimitive.Root
    ref={ref}
    className={cn(
      "flex items-center rounded-md border border-zinc-200 bg-transparent px-3 py-1 text-sm shadow-sm ring-offset-white placeholder:text-zinc-500 focus:outline-none focus:ring-1 focus:ring-zinc-950 disabled:cursor-not-allowed disabled:opacity-50 dark:border-zinc-800 dark:ring-offset-zinc-950 dark:placeholder:text-zinc-400 dark:focus:ring-zinc-300",
      className
    )}
    {...props}
  >
    {children}
  </CheckboxPrimitive.Root>
));

CheckboxTrigger.displayName = CheckboxPrimitive.Root.displayName;

const CheckboxIndicator = React.forwardRef<
  React.ElementRef<typeof CheckboxPrimitive.Indicator>,
  React.ComponentPropsWithoutRef<typeof CheckboxPrimitive.Indicator>
>((props, ref) => (
  <CheckboxPrimitive.Indicator
    ref={ref}
    className={cn("h-4 w-4 flex items-center justify-center", props.className)}
    {...props}
  >
    <CheckIcon className="h-4 w-4" />
  </CheckboxPrimitive.Indicator>
));

CheckboxIndicator.displayName = CheckboxPrimitive.Indicator.displayName;

export { Checkbox, CheckboxTrigger, CheckboxIndicator };
