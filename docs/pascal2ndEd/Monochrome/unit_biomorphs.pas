unit unit_biomorphs;interfaceuses{$IFC UNDEFINED THINK_Pascal}  Files, Quickdraw, Scrap, ToolUtils, Types,{$ENDC}  unit_globals, unit_miscellaneous, unit_error;var  min: integer;procedure Develop(var biomorph: person; Here: point; ZeroMargin: boolean);procedure Delayvelop(var Biomorph: Person; Here: Point);procedure SetUpBoxes;procedure MakeGeneBox(biomorph: person);procedure Snapshot(ThisPic: Pic; box: Rect; biomorph: person);procedure ShowGeneBox(j: integer; biomorph: person);procedure ShowChangedGene(an1, an2: person);procedure DrawPic(ThisPic: Pic; Place: Point; var Biomorph: person);procedure Reproduce(parent: person; var child: person);procedure SendToClipBoard;procedure DoHighlight;procedure DoBreed;procedure DoRowMore;procedure DoRowLess;procedure DoColMore;procedure DoColLess;procedure Targetevolve(target: person);procedure Evolve(MLoc: point);procedure Chess(var genotype: person);procedure BasicTree(var genotype: person);procedure Insect(var genotype: person);implementation{$IFC UNDEFINED THINK_Pascal}uses  Events, Memory, OSUtils, QuickdrawText, SegLoad, TextUtils; {$ENDC}procedure GeneBoxTemplate;var  j: integer;begin  Width := (Prect.right - Prect.left) div 16;  with GeneBox[1] do  begin    left := box[1].left;    right := left + Width;    top := Prect.Top;    bottom := top + GenesHeight;    EraseRect(GeneBox[1]);    Framerect(GeneBox[1]);  end;  for j := 2 to 16 do    with GeneBox[j] do    begin      top := PRect.top;      bottom := top + GenesHeight;      left := GeneBox[j - 1].right;      right := left + Width;      EraseRect(GeneBox[j]);      Framerect(GeneBox[j]);    end;end; {GeneBoxTemplate}procedure ShowGeneBox(j: integer; biomorph: person);var  thestring: str255;begin  with GeneBox[j] do  begin    EraseInnerRect(GeneBox[j]);    MoveTo(left - 8 + Width div 2, top + 14);    case j of      1..9:      begin {DrawInt(biomorph.gene[j]);}        Numtostring(biomorph.gene[j], thestring);        MoveTo(left + (Width - stringwidth(thestring)) div 2, top + 14);        Drawstring(thestring);        case biomorph.dGene[j] of          Shrink:          begin            MoveTo(left + 2, top + 21);            DrawChar(chr(165));          end;          Same:            ;          Swell:          begin            MoveTo(left + 2, top + 7);            DrawChar(chr(165));          end;        end; {dGene cases}      end; {1..9}      10:        DrawInt(biomorph.SegNoGene);      11:      begin        DrawInt(biomorph.SegDistGene);        case biomorph.dGene[10] of          Shrink:          begin            MoveTo(left + 2, top + 21);            DrawChar(chr(165));          end;          Same:            ;          Swell:          begin            MoveTo(left + 2, top + 7);            DrawChar(chr(165));          end;        end; {dGene cases}      end;      12:      begin        MoveTo(left + 2, top + 14);        case Biomorph.CompletenessGene of          single:            Drawstring(AsymString);          double:            Drawstring(BilatString)        end;      end;      13:      begin        MoveTo(left + 2, Top + 14);        case Biomorph.SpokesGene of          NorthOnly:            DrawString(SingleString);          NSouth:            DrawString(UpDnString);          Radial:            DrawString(RadialString);        end;      end;      14:        DrawInt(biomorph.tricklegene);      15:        DrawInt(biomorph.MutSizegene);      16:        Drawint(biomorph.MutProbGene);    end; {Gene Cases}  end; {WITH GeneBox}end; {ShowGeneBox}procedure ShowChangedGene(an1, an2: person);var  k: integer;begin  if OldBox > 0 then  begin    for k := 1 to 9 do      if (an1.gene[k] <> an2.gene[k]) or (an1.dgene[k] <> an2.dgene[k]) then        ShowGeneBox(k, an1);    if (an1.dgene[10] <> an2.dgene[10]) then      ShowGeneBox(k, an1);    if an1.SegNoGene <> an2.SegNoGene then      ShowGeneBox(10, an1);    if (an1.SegDistGene <> an2.SegDistGene) or (an1.dgene[10] <> an2.dgene[10]) then      ShowGeneBox(11, an1);    if an1.CompletenessGene <> an2.CompletenessGene then      ShowGeneBox(12, an1);    if an1.SpokesGene <> an2.SpokesGene then      ShowGeneBox(13, an1);    if an1.TrickleGene <> an2.TrickleGene then      ShowGeneBox(14, an1);    if an1.MutSizeGene <> an2.MutSizeGene then      ShowGeneBox(15, an1);    if an1.MutProbGene <> an2.MutProbGene then      ShowGeneBox(16, an1);  end;end; {ShowChangedGene}procedure MakeGeneBox(biomorph: person);var  j: integer;begin  GeneBoxTemplate;  for j := 1 to 16 do    ShowGeneBox(j, biomorph);end; {MakeGeneBox}function Direction9: integer;begin  if randint(2) = 2 then    direction9 := 1  else    direction9 := -1;end;function Direction(child: person): integer;begin  if randint(2) = 2 then    direction := child.MutSizegene  else    direction := -child.MutSizegene;end;procedure Reproduce(parent: person; var child: person);var  RI, j: integer;begin  child := parent;  with child do  begin    if Mut[7] then      if Randint(100) < MutProbGene then      begin        repeat          MutProbGene := MutProbGene + direction9;        until (abs(MutProbGene) <= 100) and (MutProbGene <> 0);      end;    for j := 1 to 8 do      if Randint(100) < MutProbGene then        Gene[j] := Gene[j] + direction(child);    if Randint(100) < MutProbGene then      Gene[9] := Gene[9] + direction9;    if Gene[9] < 1 then      Gene[9] := 1;    SizeWorry := SegNoGene * TwoToThe(gene[9]);    if SizeWorry > WorryMax then      Gene[9] := Gene[9] - 1;    if Mut[1] then      if RandInt(100) < MutProbGene then      begin        j := direction9;        SegNoGene := SegNoGene + j;        if j > 0 then        begin          SizeWorry := SegNoGene * TwoToThe(gene[9]);          if SizeWorry > WorryMax then            SegNoGene := SegNoGene - 1;        end;      end;    if SegNoGene < 1 then      SegNoGene := 1;    if (Mut[2]) and (SegNoGene > 1) then    begin      for j := 1 to 8 do        if Randint(100) < MutProbGene div 2 then          dGene[j] := RandSwell(dgene[j]);      if Randint(100) < MutProbGene div 2 then        dGene[10] := RandSwell(dgene[10]);    end;    if Mut[8] then      if (Mut[9] and (randint(100) < MutProbGene)) then        dGene[9] := RandSwell(dGene[9]);    if (Mut[1]) and (SegNoGene > 1) then      if Randint(100) < MutProbGene then        SegDistGene := SegDistGene + Direction9;    if Mut[3] then      if Randint(100) < MutProbGene div 2 then        if CompletenessGene = single then          CompletenessGene := double        else          CompletenessGene := single;    if Mut[4] then      if Randint(100) < MutProbGene div 2 then        case SpokesGene of          NorthOnly:            SpokesGene := NSouth;          NSouth:          begin            if Direction9 = 1 then              SpokesGene := Radial            else              SpokesGene := NorthOnly;          end;          Radial:            SpokesGene := NSouth        end;    if Mut[5] then      if Randint(100) < abs(MutProbGene) then      begin        TrickleGene := Tricklegene + direction9;        if TrickleGene < 1 then          TrickleGene := 1;      end;    if Mut[6] then      if Randint(100) < abs(MutProbGene) then      begin        MutSizeGene := MutSizeGene + direction9;        if MutSizeGene < 1 then          MutSizeGene := 1;      end;  end;end; {reproduce}function Sgn(x: integer): integer;begin  if x < 0 then    sgn := -1  else if x > 0 then    sgn := 1  else    sgn := 0;end; {sgn}procedure Slide(LiveRect, DestRect: Rect);var  SlideRect: RECT;  xDiscrep, yDiscrep, dh, dv, dx, dy, xmoved, ymoved, xToMove, yToMove,  distx, disty: integer;  TickValue: longint;  upregion: RgnHandle;    {moved from globals}begin {PenMode(PatXor); FrameRect(LiveRect); PenMode(PatCopy);}  upregion := NewRgn;    {moved from initialize}  xMoved := 0;  yMoved := 0;  distx := DestRect.left - LiveRect.left;  disty := DestRect.bottom - LiveRect.bottom;  dx := sgn(distx);  dy := sgn(disty);  xToMove := ABS(distx);  yToMove := ABS(disty);  xMoved := 0;  yMoved := 0;  UnionRect(LiveRect, DestRect, SlideRect);  ObscureCursor;  repeat    TickValue := TickCount;    xDiscrep := xToMove - xMoved;    if xDiscrep <= 20 then      dh := xDiscrep    else      dh := (xDiscrep) div 2;    yDiscrep := yToMove - yMoved;    if Ydiscrep <= 20 then      dv := yDiscrep    else      dv := (yDiscrep) div 2;    repeat    until TickValue <> TickCount;    if (xMoved < xToMove) or (yMoved < yToMove) then      ScrollRect(SlideRect, dx * dh, dy * dv, upregion);    xMoved := xMoved + ABS(dh);    yMoved := yMoved + ABS(dv);  until (xMoved >= xToMove) and (yMoved >= yToMove);end; {Slide}procedure SetUpBoxes;var  j, l, t, row, column, boxwidth: integer;begin  j := 0;  NBoxes := NRows * NCols;  MidBox := NBoxes div 2 + 1;  NActiveBoxes := NBoxes;  with Prect do  begin    boxwidth := (right - left) div ncols;    Height := (bottom - top - GenesHeight) div nrows;    for row := 1 to nrows do      for column := 1 to ncols do      begin        j := j + 1;        l := left + boxwidth * (column - 1);        t := top + GenesHeight + Height * (row - 1);        setrect(box[j], l, t, l + boxwidth, t + Height);        if (TheMode = breeding) and (j <> MidBox) then          FrameRect(box[j]);        with box[j] do        begin          Centre[j].h := left + boxwidth div 2;          CENTRE[j].v := top + Height div 2;        end;      end; {row & column loop}  end; {WITH Prect}  if theMode = breeding then  begin    PenSize(3, 3);    FrameRect(box[MidBox]);    PenSize(1, 1);  end;  with BusinessPart do  begin    left := box[1].left;    right := Box[NBoxes].right;    top := box[1].top;    bottom := box[Nboxes].bottom;  end;end; {setup boxes}procedure DoShowBoxes;begin  SetUpBoxes;  BoxesOnly;end;procedure PicLine(var ThisPic: Pic; x, y, xnew, ynew, thick: integer);begin  if thick > 8 then    thick := 8;  with ThisPic do  begin    if PicSize >= PicSizeMax then    begin      {Message(GetString(TooLargeString));}      {used the help dialog! v1.1 changed to alert}      DisplayError(-147, 'Biomorph too large, or other problem', ' ', StopError);      ExitToShell;    end    else      with MovePtr^ do      begin        StartPt.h := x;        StartPt.v := y;        EndPt.h := xnew;        EndPt.v := ynew;        Thickness := Thick;      end;    MovePtr := linptr(size(MovePtr) + 10);  {advance 'array subscript' by number}    {                                    of bytes occupied by one lin}    PicSize := PicSize + 1;  end;end; {PicLine}procedure ZeroPic(var ThisPic: Pic; Here: Point);begin  with ThisPic do  begin    MovePtr := LinPtr(BasePtr);    PicSize := 0;    Origin := Here;  end;end; {ZeroPic}type  PicStyleType = (LF, RF, FF, LUD, RUD, FUD, LSW, RSW, FSW);procedure ActualLine(Place: Point; ThisPic: Pic; PicStyle: PicStyleType;  Orientation: Compass);var  mid2, belly2: integer;  VertOffset, HorizOffset: integer;  x0, y0, x1, y1: integer;begin  {These calculations for Mid2 and Belly2 were originally done once and for}  {all outside the main loop in the calling procedure, when ActualLine was}  {a nested procedure. Now that I've factored ActualLine into a top level}  {procedure, Mid2 and Belly2 are}  {wastefully recalculated here in the Second Edition on every call, to}  {simplify the procedure's parameter signature. - ABC.}  Mid2 := 2 * Place.h;  belly2 := 2 * Place.v;  with ThisPic.MovePtr^ do  begin    Pensize(Thickness, Thickness);    if Orientation = NorthSouth then    begin      VertOffset := ThisPic.Origin.v - Place.v;      HorizOffset := ThisPic.Origin.h - Place.h;      y0 := StartPt.v - VertOffset;      y1 := EndPt.v - VertOffset;      x0 := StartPt.h - HorizOffset;      x1 := EndPt.h - HorizOffset;    end    else    begin      VertOffset := ThisPic.Origin.h - Place.v;      HorizOffset := ThisPic.Origin.v - Place.h;      y0 := StartPt.h - VertOffset;      y1 := EndPt.h - VertOffset;      x0 := StartPt.v - HorizOffset;      x1 := EndPt.v - HorizOffset;    end;    case PicStyle of      LF:      begin        MoveTo(x0, y0);        LineTo(x1, y1);      end;      RF:      begin        MoveTo(Mid2 - x0, y0);        LineTo(Mid2 - x1, y1);      end;      FF:      begin        MoveTo(x0, y0);        LineTo(x1, y1);        MoveTo(Mid2 - x0, y0);        LineTo(Mid2 - x1, y1);      end;      LUD:      begin        MoveTo(x0, y0);        LineTo(x1, y1);        MoveTo(Mid2 - x0, belly2 - y0);        LineTo(Mid2 - x1, belly2 - y1);      end;      RUD:      begin        MoveTo(Mid2 - x0, y0);        LineTo(Mid2 - x1, y1);        MoveTo(x0, belly2 - y0);        LineTo(x1, belly2 - y1);      end;      FUD:      begin        MoveTo(x0, y0);        LineTo(x1, y1);        MoveTo(Mid2 - x0, y0);        LineTo(Mid2 - x1, y1);        MoveTo(x0, belly2 - y0);        LineTo(x1, belly2 - y1);        MoveTo(Mid2 - x0, belly2 - y0);        LineTo(Mid2 - x1, belly2 - y1);      end;    end; {CASES}  end;end; {ActualLine}procedure DrawPic(ThisPic: Pic; Place: Point; var Biomorph: person);{Pic already contains its own origin, meaning the coordinates at which}{ it was originally drawn. Now draw it at Place}var  j: integer;  PicStyle: PicStyleType;begin  PicStyle := FF; {To correct initialisation bug, due to call in DoUpdate}  with biomorph do    case CompletenessGene of      single:        case SpokesGene of          NorthOnly:            PicStyle := LF;          NSouth:            PicStyle := LUD;          Radial:            PicStyle := LUD;        end;      double:        case SpokesGene of          NorthOnly:            PicStyle := FF;          NSouth:            PicStyle := FUD;          Radial:            PicStyle := FUD;        end;    end; {CASES}  {Useless because immediately overridden by ActualLine - ABC 1/16/2017  }  {PenSize(MyPenSize, MyPenSize);}  with ThisPic do  begin    MovePtr := linptr(BasePtr); {reposition at base of grabbed space}    for j := 1 to PicSize do      with Biomorph do      begin        ActualLine(Place, ThisPic, PicStyle, NorthSouth); {sometimes rangecheck error}        if SpokesGene = Radial then        begin          if CompletenessGene = single then            ActualLine(Place, ThisPic, RUD, EastWest)          else            ActualLine(Place, ThisPic, PicStyle, EastWest);        end;        ThisPic.MovePtr := linptr(size(ThisPic.MovePtr) + 10);      end;  end;  PenSize(1, 1);end; {DrawPic}procedure Snapshot(ThisPic: Pic; box: Rect; biomorph: person);var  Midpoint: Point;  SavePort: GrafPtr;  SaveBitMap: BitMap;begin  if theMode = Sweeping then    ClipRect(PRect)  else    ClipRect(businessPart);  with box do  begin    MidPoint.h := left + (right - left) div 2;    MidPoint.v := top + (bottom - top) div 2;  end;  GetPort(SavePort);  SaveBitMap := SavePort^.PortBits;  SetPortBits(AlbumBitMap[0]);  EraseRect(box); {offscreen}  DrawPic(ThisPic, MidPoint, biomorph);  SetPort(SavePort);  SetPortBits(SaveBitMap);  CopyBits(AlbumBitMap[0], SavePort^.PortBits, box, box, srcCopy, nil);  ClipRect(MainPtr^.PortRect);  {Pause('End of Snapshot ');}end; {Snapshot}type  DeltaArrayPtr = ^DeltaArray;  DeltaArray = record    dx, dy: array[0..7] of integer;    order: integer;    oddOne: boolean;  end;procedure Tree(Biomorph: person; DeltaRecordPtr: DeltaArrayPtr;  x, y, lgth, dir: integer);var  xnew, ynew: integer;  thick: integer;begin  if dir < 0 then    dir := dir + 8;  if dir >= 8 then    dir := dir - 8;  if biomorph.tricklegene < 1 then    biomorph.tricklegene := 1;  with DeltaRecordPtr^ do  begin    xnew := x + lgth * dx[dir] div biomorph.tricklegene;    ynew := y + lgth * dy[dir] div biomorph.tricklegene;    with margin do    begin      if x < left then        left := x;      if x > right then        right := x;      if y > bottom then        bottom := y;      if y < top then        top := y;      if xnew < left then        left := xnew;      if xnew > right then        right := xnew;      if ynew > bottom then        bottom := ynew;      if ynew < top then        top := ynew;    end;    if biomorph.dGene[9] = shrink then      thick := lgth    else if biomorph.dGene[9] = swell then      thick := 1 + biomorph.Gene[9] - lgth    else      thick := 1;    PicLine(MyPic, x, y, xnew, ynew, thick * MyPenSize);    if (lgth > 1) then    begin      if oddone then      begin        tree(biomorph, DeltaRecordPtr, xnew, ynew, lgth - 1, dir + 1);        if lgth < order then          tree(biomorph, DeltaRecordPtr, xnew, ynew, lgth - 1, dir - 1);      end      else      begin        tree(biomorph, DeltaRecordPtr, xnew, ynew, lgth - 1, dir - 1);        if lgth < order then          tree(biomorph, DeltaRecordPtr, xnew, ynew, lgth - 1, dir + 1);      end;    end;  end;end; {tree}{ 3 2 1 }{ 4   0 }{ 5 6 7 }{-10, -20, -20, -15, -15, 0, 15, 15, 7}procedure PlugIn(Gene: chromosome; DeltaRecordPtr: DeltaArrayPtr);begin  with DeltaRecordPtr^ do  begin    order := gene[9]; {7}    dx[3] := gene[1]; {-10}    dy[3] := gene[5]; {-15}    dx[1] := -dx[3];  {10}    dy[1] := dy[3];   {15}    dx[4] := gene[2]; {-20}    dy[4] := gene[6]; {0}    dx[0] := -dx[4];  {20}    dy[0] := dy[4];   {0}    dx[5] := gene[3]; {-20}    dy[5] := gene[7]; {15}    dx[7] := -dx[5];  {20}    dy[7] := dy[5];   {15}    dy[2] := gene[4]; {-15}    dx[2] := 0;       {0}    dy[6] := gene[8]; {15}    dx[6] := 0;       {0}  end;end; {PlugIn}procedure Develop(var biomorph: person; Here: point; ZeroMargin: boolean);var  DeltaRecord: DeltaArray;  DeltaRecordPtr: DeltaArrayPtr;  j, seg, Upextent, Downextent, wid, ht, SizeWorry, thick: integer;  Running: chromosome;  OldHere, Centre: Point;  ExtraDistance, IncDistance: integer;begin {develop}  ClipBoarding := False;  if zeromargin then    with margin do    begin      left := Here.h;      right := Here.h;      top := Here.v;      bottom := Here.v;    end;  Centre := Here;  DeltaRecordPtr := @DeltaRecord;  PlugIn(Biomorph.gene, DeltaRecordPtr);  ZeroPic(MyPic, Here);  with biomorph do    with DeltaRecord do    begin      if SegNoGene < 1 then        SegNoGene := 1;      if dGene[10] = Swell then        Extradistance := Tricklegene      else if dGene[10] = Shrink then        Extradistance := -Tricklegene      else        ExtraDistance := 0;      Running := gene;      IncDistance := 0;      for seg := 1 to SegNoGene do      begin        OddOne := odd(seg);        if seg > 1 then        begin          OldHere := Here;          Here.v := Here.v + (SegDistGene + IncDistance) div Tricklegene;          IncDistance := IncDistance + ExtraDistance;          if biomorph.dGene[9] = shrink then            thick := biomorph.Gene[9]          else            thick := 1;          PicLine(MyPic, OldHere.h, Oldhere.v, Here.h, Here.v, thick);          for j := 1 to 8 do          begin            if dGene[j] = Swell then              Running[j] := Running[j] + Tricklegene;            if dGene[j] = Shrink then              Running[j] := Running[j] - Tricklegene;          end;          if Running[9] < 1 then            Running[9] := 1;          PlugIn(Running, DeltaRecordPtr);        end;        SizeWorry := biomorph.SegNoGene * TwoToThe(biomorph.gene[9]);        if SizeWorry > WorryMax then          biomorph.Gene[9] := biomorph.Gene[9] - 1;        if biomorph.gene[9] < 1 then          biomorph.gene[9] := 1;        tree(biomorph, DeltaRecordPtr, Here.h, Here.v, order, 2);      end;    end;  with biomorph do    with margin do    begin      if Centre.h - left > right - Centre.h then        right := Centre.h + (Centre.h - left)      else        left := Centre.h - (right - Centre.h);      Upextent := Centre.v - top; {can be zero if biomorph goes down}      Downextent := bottom - Centre.v;      if ((SpokesGene = NSouth) or (SpokesGene = Radial)) or (TheMode = Engineering) then        {Obscurely necessary to cope with erasing last Rect in Manipulation}      begin        if UpExtent > DownExtent then          bottom := Centre.v + UpExtent        else          top := Centre.v - DownExtent;      end;      if SpokesGene = Radial then      begin        wid := right - left;        ht := bottom - top;        if wid > ht then        begin          top := centre.v - wid div 2 - 1;          bottom := centre.v + wid div 2 + 1;        end        else        begin          left := centre.h - ht div 2 - 1;          right := centre.h + ht div 2 + 1;        end;      end;    end;  MyPic.PicPerson := biomorph;end; {develop}procedure Delayvelop(var Biomorph: Person; Here: Point);var  margcentre, offset: integer;  OffCentre: Point;begin  develop(Biomorph, Here, true);  with margin do    margcentre := top + (bottom - top) div 2;  offset := margcentre - Here.v;  with Margin do  begin    Top := Top - Offset;    Bottom := Bottom - Offset;  end;  with OffCentre do  begin    h := Here.h;    v := Here.v - offset;  end;  DrawPic(MyPic, offcentre, Biomorph);end; {Delayvelop}function BigDiscrep(present, ideal: person): integer;var  s: integer;begin  s := abs(present.SegNoGene - ideal.SegNoGene);  s := s + abs(present.SegDistGene - ideal.SegDistGene);  s := s + abs(present.tricklegene - ideal.tricklegene);  if present.CompletenessGene <> ideal.CompletenessGene then    s := s + 1;  if present.Spokesgene <> ideal.Spokesgene then    s := s + 1;  Bigdiscrep := s;end; {BigDiscrep}function Discrep(present, ideal: person): integer;var  j: integer;  s, d: integer;begin  s := 0; {BigDiscrep(present, ideal);}  for j := 1 to 9 do  begin    d := present.gene[j] - ideal.gene[j];    s := s + d * d;  end;  discrep := s;end; {discrep}procedure TargetEvolve(target: person);var  j: integer;  exit: boolean;  BestSoFar: integer;  thisDiscrep: integer;  bytesToSave: longint;  err: OSErr;begin  Exit := False;  GlobalToLocal(Mloc);  repeat    for j := 1 to NBoxes do    begin      thisDiscrep := discrep(child[j], target);      if thisDiscrep < min then      begin        min := thisDiscrep;        bestSoFar := j;      end;    end;    Special := bestSoFar;    if special > 0 then    begin      if min < 10 then        child[special].mutsizegene := 1;      ObscureCursor;      for j := 1 to NBoxes do        if j <> special then          EraseRect(box[j]);      PenPat(white);      Framerect(box[special]);      PenPat(black);      Slide(box[special], box[MidBox]);      child[MidBox] := child[special];      SetUpBoxes;      PenSize(3, 3);      Framerect(box[MidBox]);      PenSize(1, 1);      for j := 1 to MidBox - 1 do      begin        reproduce(child[MidBox], child[j]);        ClipRect(Box[j]);        delayvelop(Child[j], Centre[j]);      end;      for j := MidBox + 1 to NBoxes do      begin        reproduce(child[MidBox], child[j]);        ClipRect(Box[j]);        delayvelop(Child[j], Centre[j]);      end;    end;    ClipRect(Prect);    special := MidBox;    if fossilizing then      bytesToSave := SizeOfPerson;    err := FSWrite(Slides, bytesToSave, @child[MidBox]);    {Need to check these error codes - Alun}    if min < 20 then      child[midbox].mutsizegene := 1;    if GetNextEvent(mDownMask, theEvent) then      exit := True;    ShowChangedGene(child[MidBox], target);    {writeln(generation, '   ', min);}  until (min = 0) or exit;  if min = 0 then  begin    StoreOffScreen(MainPtr^.PortRect, MyBitMap);    Sysbeep(1);  end;end; {Targetevolve}procedure GrowChild(j: integer);var  k: longint;begin  Cliprect(Prect);  PenMode(PatXor);  MoveTo(Centre[Midbox].h, Centre[Midbox].v);  LineTo(Centre[j].h, Centre[j].v);  k := TickCount;  repeat  until TickCount >= k + 2;  MoveTo(Centre[Midbox].h, Centre[Midbox].v);  LineTo(Centre[j].h, Centre[j].v);  PenMode(PatCopy);  reproduce(child[MidBox], child[j]);  ClipRect(Box[j]);  delayvelop(Child[j], Centre[j]);end;procedure Evolve(MLoc: point);var  j: integer;  bytesToSave: longint;  err: OSErr;begin  GlobalToLocal(Mloc);  j := 0;  repeat    j := j + 1  until (PtInRect(Mloc, box[j])) or (j > NBoxes);  if j <= NBoxes then    special := j  else    special := 0;  if special > 0 then  begin    ObscureCursor;    for j := 1 to NBoxes do      if j <> special then        EraseRect(box[j]);    PenPat(white);    Framerect(box[special]);    PenPat(black);    Slide(box[special], box[MidBox]);    child[MidBox] := child[special];    SetUpBoxes;    {delayvelop(Child[MidBox],Centre[MidBox]);}    for j := 1 to MidBox - 1 do      Growchild(j);    for j := MidBox + 1 to NBoxes do      Growchild(j);  end;  ClipRect(Prect);  special := MidBox;  if fossilizing then  begin    bytesToSave := SizeOfPerson;    err := FSWrite(Slides, bytesToSave, @child[MidBox]);    FossilsToSave := True;  end;{****}end; {evolve}procedure DoBreed;var  p: point;begin  TheMode := breeding;  OldBox := special;  EraseRect(PRect);  SetUpBoxes;  OldSpecial := 0;  SetCursor(CursList[watchCursor]^^);  Child[MidBox] := child[special];  Special := MidBox;  MakeGeneBox(Child[special]);  Delayvelop(Child[Special], Centre[MidBox]);  p := centre[MidBox];  p.v := box[MidBox].bottom - 1;  Evolve(p);  ClipRect(Box[MidBox]);  Delayvelop(Child[Special], Centre[MidBox]);  ClipRect(PRect);  StoreOffScreen(MainPtr^.PortRect, MyBitMap);end; {DoBreed}procedure DoHighlight;var  j: integer;  Ticking: boolean;  TickValue: longint;begin  Ticking := False;  if (TheMode = Phyloging) or (TheMode = Moving) or (TheMode = Detaching) or    (TheMode = Killing) then {OR (theMode=Noahing)}  begin    InvertRect(SpecialFull^^.Surround);    TickValue := TickCount;    Ticking := True;  end;  if (theMode = breeding) or (TheMode = Drifting) then  begin    OldSpecial := Special;    InvertRect(box[Special]);    TheMode := Highlighting;    MakeGeneBox(child[special]);  end;  if theMode = Albuming then    OldSpecial := Special;  if Ticking then  begin    repeat    until TickCount - TickValue >= 30;    InvertRect(SpecialFull^^.Surround);  end;end; {DoHighlighting}procedure DoRowMore;begin  Nrows := Nrows + 2;  if NRows * Ncols > MaxBoxes then    NRows := NRows - 2;  EraseRect(Prect);  DoShowBoxes;  if special > 0 then    Delayvelop(Child[Special], Centre[MidBox]);  OldSpecial := 0;end; {DoRowMore}procedure DoRowLess;begin  if Nrows > 1 then    Nrows := Nrows - 2;  EraseRect(Prect);  DoShowBoxes;  if special > 0 then    Delayvelop(Child[Special], Centre[MidBox]);  OldSpecial := 0;end; {DoRowLess}procedure DoColMore;begin  Ncols := Ncols + 2;  if NRows * Ncols > MaxBoxes then    NCols := NCols - 2;  EraseRect(Prect);  DoShowBoxes;  if special > 0 then    Delayvelop(Child[Special], Centre[MidBox]);  OldSpecial := 0;end; {DoColMore}procedure DoColLess;begin  if NCols > 1 then    NCols := Ncols - 2;  EraseRect(Prect);  DoShowBoxes;  if special > 0 then    Delayvelop(Child[Special], Centre[MidBox]);  OldSpecial := 0;end; {DoColLess}procedure PictureToScrap;var  LENGTH: longint;  Source: Ptr;begin  if ZeroScrap <> NoErr then  begin    Sysbeep(1); {write('ZeroScrap Error')}  end  else  begin    HLock(handle(MyPicture));    Length := MyPicture^^.PicSize;    Source := Ptr(MyPicture^);    if PutScrap(Length, 'PICT', Ptr(MyPicture^)) <> NoErr then      ExitToShell;    HUnlock(handle(MyPicture));  end;  ClipBoarding := True;end;procedure SendToClipBoard;var  HS: integer;  errString, helpString: Str255;begin  MyPicture := OpenPicture(Box[MidBox]);  Delayvelop(Child[Special], Centre[MidBox]);  CopiedAnimal := Child[special];  ClosePicture;  HS := GetHandleSize(Handle(MyPicture));  if (HS = 0) or (HS > 32000) then  begin    errString := GetString(131)^^;    helpString := GetString(132)^^;    DisplayError(0, errString, helpString, StopError);    {was: (uses Help dialog! ) Message(GetString(TooLargeString))}  end  else    PictureToScrap;  KillPicture(MyPicture);end; {SendToClipBoard}procedure MakeGenes(var genotype: person; a, b, c, d, e, f, g, h, i: integer);var  j: integer;begin  with genotype do  begin    for j := 1 to 10 do      dgene[j] := same;    SegNoGene := 1;    SegDistGene := 150;    CompletenessGene := double;    SpokesGene := NorthOnly;    TrickleGene := Trickle;    MutSizeGene := Trickle div 2;    MutProbGene := 10;    gene[1] := a;    gene[2] := b;    gene[3] := c;    gene[4] := d;    gene[5] := e;    gene[6] := f;    gene[7] := g;    gene[8] := h;    gene[9] := i;  end;end; {makegenes}procedure Chess(var genotype: person);begin  makegenes(genotype, -trickle, 3 * trickle, -3 * trickle, -3 * trickle,    trickle, -2 * trickle, 6 * trickle, -5 * trickle, 7);end; {chess}procedure BasicTree(var genotype: person);var  j: integer;begin  makegenes(genotype, -10, -20, -20, -15, -15, 0, 15, 15, 7);  with genotype do  begin    SegNoGene := 2;    SegDistGene := 150;    CompletenessGene := single;    dgene[4] := shrink;    dgene[5] := shrink;    dgene[6] := shrink;    dgene[9] := shrink;    tricklegene := 9;  end;end; {root}procedure Insect(var genotype: person);begin  makegenes(genotype, trickle, trickle, -4 * trickle, trickle, -trickle,    -2 * trickle, 8 * trickle, -4 * trickle, 6);end; {insect}end.