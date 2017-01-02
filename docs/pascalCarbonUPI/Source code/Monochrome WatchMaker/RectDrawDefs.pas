unit RectDrawDefs;

interface

uses MacOSAll;
procedure FrameOuterRect (box: Rect);
procedure FrameInnerRect (box: Rect);
procedure EraseInnerRect (box: Rect);
procedure BigFuncBox (Box: Rect; VAR Outbox: Rect);
procedure SmallFuncBox (Box: Rect; VAR Outbox: Rect; BoxSize: Integer);
procedure FuncBox (Box: Rect; VAR Outbox: Rect; BoxSize: Integer);

implementation

procedure BigFuncBox (Box: Rect; VAR Outbox: Rect);
	begin
		Outbox := box;
		WITH Box DO
			begin
				IF Bottom - Top > Right - Left THEN
					begin
						Outbox.right := (Right + Left + Bottom - Top) DIV 2;
						Outbox.left := outbox.right - (bottom - top)
					end
				ELSE
					begin
						Outbox.bottom := (Top + Bottom + Right - Left) DIV 2;
						Outbox.Top := Outbox.bottom - (right - left)
					end
			end;
	end; {FuncBox}

procedure SmallFuncBox (Box: Rect; VAR Outbox: Rect; BoxSize: Integer);
	begin
		Outbox := box;
		WITH Box DO
			begin
				Outbox.Right := (Left + Right + BoxSize) DIV 2;
				Outbox.left := Outbox.right - BoxSize;
				Outbox.bottom := (top + bottom + Boxsize) DIV 2;
				Outbox.top := Outbox.bottom - Boxsize
			end;
	end; {SmallFuncBox}

procedure FuncBox (Box: Rect; VAR Outbox: Rect; BoxSize: Integer);
	begin
		WITH Box DO
			IF ((Right - Left) > BoxSize) OR ((Bottom - Top) > Boxsize) THEN
				BigFuncBox(Box, Outbox)
			ELSE
				SmallFuncBox(Box, Outbox, Boxsize);
		WITH Outbox DO
			begin
				right := right + 1;
				bottom := bottom + 1;
			end;
	end;


procedure EraseInnerRect (box: Rect);
	VAR
		InnerRect: Rect;
	begin
		WITH InnerRect DO
			begin
				left := box.left + 1;
				right := box.right - 1;
				top := box.top + 1;
				bottom := box.bottom - 1
			end;
		EraseRect(InnerRect)
	end; {EraseInnerRect}


procedure FrameOuterRect (box: Rect);
	VAR
		OuterRect: Rect;
	begin
		WITH OuterRect DO
			begin
				left := box.left - 1;
				right := box.right + 1;
				top := box.top - 1;
				bottom := box.bottom + 1
			end;
		FrameRect(OuterRect)
	end; {FrameOuterRect}


procedure FrameInnerRect (box: Rect);
	VAR
		InnerRect: Rect;
	begin
		WITH InnerRect DO
			begin
				left := box.left + 1;
				right := box.right - 1;
				top := box.top + 1;
				bottom := box.bottom - 1
			end;
		FrameRect(InnerRect)
	end; {FrameInnerRect}

end.