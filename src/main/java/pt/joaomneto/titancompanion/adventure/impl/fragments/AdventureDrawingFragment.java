package pt.joaomneto.titancompanion.adventure.impl.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.AdventureFragment;
import pt.joaomneto.titancompanion.view.DrawingView;

public class AdventureDrawingFragment extends AdventureFragment {

    Boolean loadFile = false;
    private float smallBrush, mediumBrush, largeBrush;
    private ImageButton currPaint, drawBtn, eraseBtn, newBtn;
    private DrawingView drawView;
    private Bitmap imageToLoad = null;


    public AdventureDrawingFragment() {
        setBaseLayout(R.layout.fragment_adventure_drawing);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        synchronized (loadFile) {
            super.onCreate(savedInstanceState);
            View rootView = inflater.inflate(getBaseLayout(),
                    container, false);

            smallBrush = getResources().getInteger(R.integer.small_size);
            mediumBrush = getResources().getInteger(R.integer.medium_size);
            largeBrush = getResources().getInteger(R.integer.large_size);

            drawBtn = rootView.findViewById(R.id.draw_btn);

            drawView = rootView.findViewById(R.id.drawing);

            if (loadFile && imageToLoad != null) {
                drawView.loadImage(imageToLoad);
                imageToLoad = null;
                loadFile = false;
            }

            drawBtn.setOnClickListener(view -> {
                final Dialog brushDialog = new Dialog(getContext());
                brushDialog.setTitle("Brush size:");
                brushDialog.setContentView(R.layout.component_brush_chooser);
                ImageButton smallBtn = brushDialog.findViewById(R.id.small_brush);
                smallBtn.setOnClickListener(view1 -> {
                    drawView.setBrushSize(smallBrush);
                    drawView.setLastBrushSize(smallBrush);
                    drawView.setErase(false);
                    brushDialog.dismiss();
                });
                ImageButton mediumBtn = brushDialog.findViewById(R.id.medium_brush);
                mediumBtn.setOnClickListener(view1 -> {
                    drawView.setBrushSize(mediumBrush);
                    drawView.setLastBrushSize(mediumBrush);
                    drawView.setErase(false);
                    brushDialog.dismiss();
                });

                ImageButton largeBtn = brushDialog.findViewById(R.id.large_brush);
                largeBtn.setOnClickListener(view1 -> {
                    drawView.setBrushSize(largeBrush);
                    drawView.setLastBrushSize(largeBrush);
                    drawView.setErase(false);
                    brushDialog.dismiss();
                });
                brushDialog.show();
            });

            eraseBtn = rootView.findViewById(R.id.erase_btn);
            eraseBtn.setOnClickListener(view -> {
                final Dialog brushDialog = new Dialog(getContext());
                brushDialog.setTitle("Eraser size:");
                brushDialog.setContentView(R.layout.component_brush_chooser);
                ImageButton smallBtn = brushDialog.findViewById(R.id.small_brush);
                smallBtn.setOnClickListener(view1 -> {
                    drawView.setErase(true);
                    drawView.setBrushSize(smallBrush);
                    brushDialog.dismiss();
                });
                ImageButton mediumBtn = brushDialog.findViewById(R.id.medium_brush);
                mediumBtn.setOnClickListener(view1 -> {
                    drawView.setErase(true);
                    drawView.setBrushSize(mediumBrush);
                    brushDialog.dismiss();
                });
                ImageButton largeBtn = brushDialog.findViewById(R.id.large_brush);
                largeBtn.setOnClickListener(view1 -> {

                    drawView.setErase(true);
                    drawView.setBrushSize(largeBrush);
                    brushDialog.dismiss();
                });
                brushDialog.show();
            });

            newBtn = rootView.findViewById(R.id.new_btn);
            newBtn.setOnClickListener(view -> {
                AlertDialog.Builder newDialog = new AlertDialog.Builder(getContext());
                newDialog.setTitle(R.string.newDrawing);
                newDialog.setMessage(R.string.startNewDrawing);
                newDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        drawView.startNew();
                        dialog.dismiss();
                    }
                });
                newDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                newDialog.show();
            });

            drawView.setBrushSize(mediumBrush);

            return rootView;
        }

    }

    @Override
    public void refreshScreensFromResume() {

    }

    public Bitmap getDrawingBitmap() {
        return drawView.getDrawingCache();
    }

    public void setDrawingBitmap(Bitmap imageToLoad) {
        synchronized (loadFile) {
            if (drawView != null) {
                this.drawView.loadImage(imageToLoad);
                this.imageToLoad = null;
                this.loadFile = false;
            } else {
                this.imageToLoad = imageToLoad;
                this.loadFile = true;
            }
        }
    }
}
