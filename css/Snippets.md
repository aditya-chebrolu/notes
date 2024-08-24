# CSS Cheatsheet

1. **No Scroll bar**
   ```css
   ::-webkit-scrollbar {
    display: none;
   }
   ```
2. **Remove type="number"the arrow at the end**
    ```css
    input::-webkit-outer-spin-button,
    input::-webkit-inner-spin-button {
       -webkit-appearance: none;
    }
    ```
3. **Line clamp**
    ```css
    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;  
    overflow: hidden;
    ```
4. **Full bleed**
    ```css
    box-shadow: 0 0 0 1000vmax black;
    clip-path: inset(0 -100vmax);
    ```
5. **Transition to heigh auto**
    ```css
    .wrapper {
       display:grid;
       grid-template-rows:0fr;
       transition: grid-template-rows 0.5s;
    }
    .wrapper.is-open {
       grid-template-rows: 1fr;
    }
    .inner {
       overflow:hidden;
    }
    ```
 6. **Fade Gradients**
    ```css
    background: linear-gradient(to bottom, rgba(255, 255, 255, 0), rgba(0, 0, 0, 1));
    ```
 7. **Grid background**
    ```css
    background-size: 40px 40px;
    background-image:
       linear-gradient(to right, grey 1px, transparent 1px),
       linear-gradient(to bottom, grey 1px, transparent 1px);
    ```
 8. **Dotted background** **[more info](https://stackoverflow.com/questions/3540194/how-to-make-a-grid-like-graph-paper-grid-with-just-css)**
    ```css
    background-size: 40px 40px;
    background-image: radial-gradient(circle, #000000 1px, rgba(0, 0, 0, 0) 1px);
    ```

Note:
- apply overflow to flex-item might shrink it.

  fix: `flex-shrink:0`
